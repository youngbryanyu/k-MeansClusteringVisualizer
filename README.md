# k-Means Clustering Visualizer
Visualizer for color-based image segmentation in live-video using K-means clustering. Number of unique clusters is either automatically determined by program or can be set to a custom value. Applies thresholding to pixels in image/video before clustering. The k clusters are highlighted with randomly initialized colors. (SkinFilter.java class)


## Getting Started

### Prerequisites

.dll files are incompatible with Mac OSX.

All of the libraries required to run the program are included in the lib folder. Go to (File > Project Structure > Libraries > +) and configure all the .jar files. Make sure that the jdk is 64 bit and the project language level is at 8. 

### Installing

Runnable on any IDE (I prefer IntelliJ for easy setup and cloning).

```
 $ git clone https://github.com/youngbryanyu/VideoImageClustering.git
```

## Running the tests

The main class can be run from FilterView.java class. When program runs, click the window and press the lower case 'f' key. A window will pop up prompting for the name of the filter to run and type in 'SkinFilter' and press enter. Next a window will pop up where the left panel displays what the live camera captures and the right panel displays the clusters of objects. Only objects of the target color with be displayed and clustered while everything else will be blacked out. 

## Built With

* [Java 8](https://www.oracle.com/technetwork/java/javase/downloads/index.html) - language
* [Processing](https://processing.org/) - graphics
* [gluegen](https://jogamp.org/gluegen/www/) - interface and C libraries 
* [gstreamer](https://gstreamer.freedesktop.org/) - media processing
* [jna](https://github.com/java-native-access/jna) - java native access
* [jogl](http://jogamp.org/jogl/www/) - wrapper library
* [video](https://processing.org/reference/libraries/video/index.html) - video capturing

## Contributing

This project is currently open source are free for anyone to contribute. Simply create a new branch, make a pull request, and I will verify whether or not the edits are valid.

## Authors

* **Young Yu** - *Clustering implementation, Framework, and UI* - [youngbryanyu](https://github.com/youngbryanyu)

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments

* Hat tip to anyone whose code was used
