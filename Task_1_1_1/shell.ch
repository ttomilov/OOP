cd src/java
javac org/example/Main.java org/example/Heap.java
jar --create -f org/example/HeapSort.jar -m manifest/Manifest.mf org/example/*.class
java -jar org/example/HeapSort.jar