# 💬 Real-Time Chat Application (Java + JavaFX)

A real-time chat application built using Java Socket Programming and JavaFX for the graphical user interface. This project was developed as part of the ShadowFox Advanced Level Internship.

## 🚀 Features

- ✅ Multi-user chat support (server-client model)
- ✅ GUI interface with JavaFX
- ✅ Transparent chat area with a custom wallpaper
- ✅ Timestamped messages
- ✅ Clean and minimal UI with modern design
- ✅ Private messaging via `/pm` (optional)

---

## 🛠️ Tech Stack

 Java        - Core logic and networking   
 JavaFX      - GUI (controls, layouts)     
 Sockets     -Real-time communication     

## 📁 Project Structure

RealTimeChatApp/
├── src/
│ ├── ChatClient.java # Command-line client (optional)
│ ├── ChatClientGUI.java # JavaFX GUI client
│ ├── ChatServer.java # Multi-threaded chat server
│ └── wallpaper.jpg # Background image for chat window
├── README.md


---

## ⚙️ Requirements

- Java JDK 17 or above  
- JavaFX SDK 21 or 24 (download from [https://openjfx.io](https://openjfx.io))

---

## ▶️ How to Run

1️⃣ Compile all files

-bash
cd RealTimeChatApp/src
javac --module-path "C:/javafx-sdk-24/lib" --add-modules javafx.controls ChatClientGUI.java ChatClient.java ChatServer.java

2️⃣ Start the server (in a separate terminal)

java ChatServer

3️⃣ Start the client GUI

java --module-path "C:/javafx-sdk-24/lib" --add-modules javafx.controls ChatClientGUI

---

🖼️ Customization

To change the background, replace wallpaper.jpg with any image of your choice (same name).

🙋‍♀️ Developer
Pavitra
Intern at ShadowFox
LinkedIn: linkedin.com/in/pavitralk

"Built with passion as part of ShadowFox’s real-world virtual internship experience.” 
