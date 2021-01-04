Das Programm wurde unter JDK 1.7 kompiliert.
Das Programm kann mit zwei verschiedenen Wegen durchgeführt werden:

(I) MainFrame.java im Package GUI ausführen und somit an die Grafikoberfläche gelangen. 
Hier können Bilder ausgewählt, geladen (bzw. neu-geladen) werden. 
Mithilfe der beiden Zahlenregler lassen sich die Anzahl der zu entfernenden Seams für X und Y einstellen, und mit Execute wird das SeamCarving auf das aktuelle Bild angewendet werden. Die Operationen können auf dem geänderten Bild erneut ausgeführt werden - soll das aktuelle Bild auf das Originalbild zurückgesetzt werden, so ist dies möglich über "Load Image".
Mit "Save" wird das aktuelle Bild dann in demselben Ordner wie das Ursprungsbild gespeichert (das Original wird nicht überschrieben).

(II) Über die Kommandozeile mit den Argumenten:
java SeamCarving.jar [Pfad zu Originalbild] [Anzahl der zu entfernenden X-Seams] [Anzahl der zu entfernenden Y-Seams]
  z.B. >java SeamCarving.jar "C:\Picture.jpg" 40 40

Das Bild wird anschließend automatisch in demselben Ordner wie das Originalbild gespeichert.
