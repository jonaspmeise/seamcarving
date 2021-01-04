Das Programm kann mit zwei verschiedenen Wegen durchgeführt werden.

(I) MainFrame.java im Package GUI ausführen und somit an die Grafikoberfläche gelangen. Hier können Bilder ausgewählt, geladen (bzw. neu-geladen) werden. Mithilfe der beiden Zahlenregler lassen sich die Anzahl der zu entfernenden Seams für X und Y einstellen, und mit Execute wird das SeamCarving auf das aktuelle Bild angewendet werden. Mit Save wird dieses Bild dann an demselben Ort wie das Ursprungsbild gespeichert.

(II) Über die Kommandozeile mit den Argumenten:
java SeamCarving.jar [Pfad zu Originalbild] [Anzahl der zu entfernenden X-Seams] [Anzahl der zu entfernenden Y-Seams]

Das Bild wird anschließend automatisch in demselben Ordner wie das Originalbild gespeichert.