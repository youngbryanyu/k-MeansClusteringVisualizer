UNFINISHED README
# Video Image Clustering
Clusters/groups objects in an image or live video using K-means clustering. The different clusters are displayed with randomly initialized different colors. Currently implemented to detect human skin surfaces and automatically determine the number of unique clusters present. Only objects of the target color with be displayed and clustered while everything else will be blacked out (Currently detects light skin color objects). 


## Getting Started

### Prerequisites

.dll files are incompatible with Mac OSX.

All of the libraries required to run the program are included in the lib folder. Go to (File > Project Structure > Libraries > +) and configure all the .jar files. Make sure that the jdk is 64 bit and the project language level is at 8. 

### Installing

Runnable on any IDE (I prefer IntelliJ for easy setup and cloning).

```
 $ git clone https://github.com/yyu2002/VideoImageClustering.git
```

## Running the tests

The main class can be run from FilterView.java class. When program runs, click the window and press the lower case 'f' key. A window will pop up prompting for the name of the filter to run and type in 'SkinFilter' and press enter. Next a window will pop up where the left panel displays what the live camera captures and the right panel displays the clusters of objects. Only objects of the target color with be displayed and clustered while everything else will be blacked out. 

## Built With

* [Processing](https://processing.org/) - graphics
* [gluegen] (https://jogamp.org/gluegen/www/)
* [gstreamer] (https://gstreamer.freedesktop.org/) 
* [jna] (https://github.com/java-native-access/jna)
* [jogl] (http://jogamp.org/jogl/www/)
* [video] (https://processing.org/reference/libraries/video/index.html)

## Contributing

Please read [CONTRIBUTING.md](https://gist.github.com/PurpleBooth/b24679402957c63ec426) for details on our code of conduct, and the process for submitting pull requests to us.

## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/your/project/tags). 

## Authors

* **Billie Thompson** - *Initial work* - [PurpleBooth](https://github.com/PurpleBooth)

See also the list of [contributors](https://github.com/your/project/contributors) who participated in this project.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments

* Hat tip to anyone whose code was used
* Inspiration
* etc
