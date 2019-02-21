package com.ballistic.coredel;

import com.mongodb.ReadPreference;
import com.mongodb.event.ClusterClosedEvent;
import com.mongodb.event.ClusterDescriptionChangedEvent;
import com.mongodb.event.ClusterListener;
import com.mongodb.event.ClusterOpeningEvent;

public class MongoClusterListener implements ClusterListener {

    private final ReadPreference readPreference;
    private boolean isWritable;
    private boolean isReadable;

    public MongoClusterListener(final ReadPreference readPreference) {
        this.readPreference = readPreference;
    }

    @Override
    public void clusterOpening(final ClusterOpeningEvent clusterOpeningEvent) {
        System.out.println(String.format("Cluster with unique client identifier %s opening", clusterOpeningEvent.getClusterId()));
    }

    @Override
    public void clusterClosed(final ClusterClosedEvent clusterClosedEvent) {
        System.out.println(String.format("Cluster with unique client identifier %s closed", clusterClosedEvent.getClusterId()));
    }

    @Override
    public void clusterDescriptionChanged(final ClusterDescriptionChangedEvent event) {
        if (!isWritable) {
            if (event.getNewDescription().hasWritableServer()) {
                isWritable = true;
                System.out.println("Writable server available!");
            }
        } else {
            if (!event.getNewDescription().hasWritableServer()) {
                isWritable = false;
                System.out.println("No writable server available!");
            }
        }

        if (!isReadable) {
            if (event.getNewDescription().hasReadableServer(readPreference)) {
                isReadable = true;
                System.out.println("Readable server available!");
            }
        } else {
            if (!event.getNewDescription().hasReadableServer(readPreference)) {
                isReadable = false;
                System.out.println("No readable server available!");
            }
        }
    }
}