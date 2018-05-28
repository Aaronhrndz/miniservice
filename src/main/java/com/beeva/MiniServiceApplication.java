package com.beeva;

import javax.inject.Singleton;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import com.beeva.db.MongoRepository;
import com.beeva.db.Repository;
import com.beeva.resources.PersonResource;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.MongoDatabase;

import io.dropwizard.Application;
import io.dropwizard.lifecycle.Managed;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class MiniServiceApplication
        extends Application<MiniServiceConfiguration> {

    public static void main(final String[] args) throws Exception {
        new MiniServiceApplication().run(args);
    }

    @Override
    public String getName() {
        return "MiniService";
    }

    @Override
    public void initialize(
            final Bootstrap<MiniServiceConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final MiniServiceConfiguration configuration,
            final Environment environment) {

        environment.jersey().register(new PersonResource());

        Repository repository = new MongoRepository();

        environment.lifecycle().manage(new Managed() {

            @Override
            public void start() {
                repository.open();
            }

            @Override
            public void stop() {
                repository.close();
            }
        });


        environment.jersey().register(new AbstractBinder() {

            @Override
            protected void configure() {
                bind(repository).to(Repository.class);
            }
        });


    }

}
