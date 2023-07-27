## Covesa Diagnostic Log and Trace (DLT) via Java

A first try to connect to a dlt-daemon and decode DLT messages.

Currently I have really no information if there is some java SW in existence that can receive and decode DLT messages.
The dlt-viewer is a QT-c++-application and there are not much extensions available (Vlad, my hero, did it once, but he's a lot tougher than me!).

The code in this repro is a hack with a lot of definitions from Dlt-Viewer-code.
The core of the decoder (DltMessage.setMsg) is created from specs. The original code from dlt-viewer was used as reference,
but was not really usable.




