![Logo](img//Kmeans.png)

K-Means Clustering is software that allows the clustering of data examples starting from a relational database table. The software is made up of three applications: a server that performs clustering, saving and loading file operations and two clients to interact with the server. For both clients (console or graphical interface) it is possible to use the same server. For ease of use, client and server use a preset IP address and port, you can change these within the code.

No complex procedures are required to run the software. However, for correct operation it is necessary that:
* An updated version of Java SDK is installed;
* MySQL service (version 5.7 or later) is installed and active;

K-Means server must be started before starting the client. Otherwise, an error message will appear, stopping the execution of the client program. Some examples:

* Client console startup example:

![StartupCC](img//startupCClient.png)

* Server info reading example:

![Server](img//ServerInfo.png)

* UI Client startup example:

![StartupUI](img//startupUIclient.png)

* UI Client reading clusters example:

![UIreading](img//ReadingClusters.png)