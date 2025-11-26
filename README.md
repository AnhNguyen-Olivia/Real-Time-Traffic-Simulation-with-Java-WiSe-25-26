# Real-time Traffic Simulation System using SUMO and Java

This project integrates the SUMO Traffic Simulator with a Java application (Eclipse IDE) using the TraCI protocol via the TraaS Java library.
The goal is to allow real-time simulation control from a Java GUI, visualize vehicle flows, and support basic traffic management operations.

---

## Project Overview

This project is a simple **real-time traffic simulation** written in Java.  
It simulates:

- Vehicles moving on a predefined road network
- Traffic lights switching between red/yellow/green states
- Roads and map layout
- A GUI built using Java Swing (similar to SUMO-GUI)  
- Real-time behavior controlled by a Swing `Timer`

## Installation

- JavaSE-11
- SUMO-gui
- Eclipse IDE

---

## Features

- Object-oriented design with multiple classes  
- Traffic lights change using timers
- Road network rendering (horizontal + vertical)  
- Smooth animations  
- Modular structure split across multiple classes  
- Easy to extend with new features
- Real-time animated environment
- GUI similar in concept to SUMO-GUI
- Configurable update frequency
- Clean, modular Java project structure

---

## Team roles

|Name                              | Role                 | Description |
|----------------------------------|----------------------|-------------|
|Pham Tran Minh Anh                | Developer, GUI Designer            | Writes Java source code, including GUI and logic. Design the layout, appearance, and user controls. Writes README. |
|Nguyen Thuy Anh                   | Developer                          | Writes Java source code, including SUMO connection, step simulation, and wrappers |
|Dieu Ngoc Thien An                | Document Writer                    | Creates diagram. |
|Huynh Bao Tran                    | Tester                             | Write documentation. |
|Nguyen Ho Tuyet Phuong            | Developer                          | Creates logic for vehicles, traffic lights, and simulation rules. |

---

## Technology Stack Summary

### Languages

- [![Java](https://img.shields.io/badge/Java-%23ED8B00.svg?logo=openjdk&logoColor=white&style=flat-square)](https://www.oracle.com/de/java/technologies/javase/jdk11-archive-downloads.html)[![v11.0.28](https://img.shields.io/badge/v11.0.28-555?style=flat-square)](https://www.oracle.com/de/java/technologies/javase/jdk11-archive-downloads.html)

### Tools and IDE

- [![Eclipse](https://img.shields.io/badge/Eclipse-FE7A16.svg?logo=Eclipse&logoColor=white&style=flat-square)](https://eclipseide.org/)[![v2025--09](https://img.shields.io/badge/v2025--09-555?style=flat-square)](https://eclipseide.org/)

- [![Visual Studio Code](https://custom-icon-badges.demolab.com/badge/Visual%20Studio%20Code-0078d7.svg?logo=vsc&logoColor=white&style=flat-square)](https://code.visualstudio.com/)[![v1.106](https://img.shields.io/badge/v1.106-555?style=flat-square)](https://code.visualstudio.com/)

- [![Git](https://img.shields.io/badge/Git-F05032?logo=git&logoColor=fff&style=flat-square)](https://git-scm.com/)[![v2.52.0](https://img.shields.io/badge/v2.52.0-555?style=flat-square)](https://git-scm.com/)

- [![SUMO](https://img.shields.io/badge/SUMO-green?logo=Java&logoColor=orange&style=flat-square)](https://sumo.dlr.de/docs/index.html)[![v1.25.0](https://img.shields.io/badge/v1.25.0-555?style=flat-square)](https://sumo.dlr.de/docs/index.html)

- [![TraaS](https://img.shields.io/badge/TraaS-green?logo=Java&logoColor=orange&style=flat-square)](https://sumo.dlr.de/docs/TraCI/TraaS.html)

- [![GitHub](https://img.shields.io/badge/GitHub-%23121011.svg?logo=github&logoColor=white&style=flat-square)](https://github.com/AnhNguyen-Olivia/Real-Time-Traffic-Simulation-with-Java-WiSe-25-26)

- [![Static Badge](https://img.shields.io/badge/Java_Swing-blue?logo=Java&logoColor=orange&style=flat-square)](https://docs.oracle.com/javase/8/docs/api/index.html?javax/swing/package-summary.html)

---

## Development Time Plan

### **Milestones Overview**

|Dates          | Milestone / Stage                  | Key Deliverables                            |
|----------------|------------------------------------|----------------------------------------------|
|Nov 1 – 27   | Milestone 1: Design & Prototype | Overview, diagrams, wrapper, mockups         |
|Nov 28 – Dec 14 | Milestone 2: Functional Prototype | Live demo, core features                     |
|Dec 15 – Jan 18 | Milestone 3: Finalize & Submission | Full GUI, export features, documentation     |

### **Detailed Checklists**

#### Milestone 1 — Design & Prototype

- [ ] Project overview (1 - 2 pages)
- [ ] Architecture diagram
- [x] Class Design for TraaS wrapper (Vehicle, TrafficLight, etc.)
- [x] GUI Mockups (map view, control panels, dashboard)
- [x] SUMO Connection Demo (list traffic lights, step simulation)
- [x] Technology Stack Summary
- [x] Git Repository Setup (README, initial commit)
- [x] Team roles & time plan documentation

#### Milestone 2 — Functional Prototype

- [ ] Running app with live SUMO ↔ Java communication
- [ ] Vehicle injection + traffic light control
- [ ] Map rendering inside GUI
- [ ] Draft Javadoc & user guide
- [ ] Test scenario (vehicles, signals, route)
- [ ] Mid-project progress summary

#### Milestone 3 — Finalization & Submission

- [ ] Final polished GUI with full interactions
- [ ] Grouping/filtering logic for vehicles
- [ ] Traffic rule logic + timing optimization
- [ ] Export features (CSV & PDF)
- [ ] All documentation completed
- [ ] Final user guide + installation section
- [ ] Presentation preparation
- [ ] Team reflection & clean repository

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
