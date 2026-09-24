# WhereIsTheSolution 🌎

> **A community-driven platform for reporting, discovering, and bringing visibility to problems happening in our cities.**

WhereIsTheSolution is a **Kotlin-first, multiplatform social platform** designed to give communities a simple and accessible place to report local problems, share information, discuss issues, and increase their visibility.

Think of it as a social network focused on **civic awareness and local communities** rather than traditional social content.

Instead of sharing what happened during your day, you can share **what is happening in your city**.

---

## 📌 ABOUT THE PROJECT

Problems in cities are often scattered across Instagram posts, WhatsApp groups, messaging apps, and different community channels.

WhereIsTheSolution aims to bring these conversations into one dedicated platform.

Users can:

* 📍 **Report local problems**
* 📸 **Share photos and descriptions**
* 💬 **Discuss issues with the community**
* 🔎 **Discover problems around them**
* 📢 **Give visibility to important issues**
* 🤝 **Interact with other members of the community**

The long-term goal is to create a simple communication layer that makes **local problems easier to discover, discuss, and follow**.

---

## 🏗️ ARCHITECTURE

The project follows a **Kotlin-first multiplatform architecture**, sharing as much code as possible between platforms while keeping clear boundaries between the client and server.

```text
WhereIsTheSolution
│
├── core
│   ├── Domain
│   ├── Data
│   ├── Shared Models
│   └── Business Logic
│
├── androidApp
│   └── Android Application
│
└── server
    ├── Ktor Server
    ├── API
    ├── Database
    └── Backend Services
```

The application follows **Clean Architecture + MVVM**, with a strong emphasis on:

* Separation of concerns
* Modularity
* Testability
* Reusable business logic
* Dependency inversion
* Cross-platform code sharing

### 🧩 CORE MODULE

The `core` module contains the shared multiplatform logic.

It includes:

* Domain models
* Use cases
* Repository contracts
* Shared business logic
* Data layer
* Network clients
* Database access
* Shared state management

The goal is to keep platform-independent logic inside the shared Kotlin codebase whenever possible.

### 📱 ANDROID APP

The Android application is built with **Compose Multiplatform**, allowing UI and application logic to remain strongly aligned with the shared Kotlin architecture.

### 🖥️ SERVER

The backend is built entirely with **Kotlin + Ktor**.

The server is responsible for:

* REST APIs
* Authentication
* Business logic
* Persistence
* Database access
* Backend services
* AI-powered workflows

---

## 🛠️ TECH STACK

### 📱 MULTIPLATFORM

| Technology                | Purpose                  |
| ------------------------- | ------------------------ |
| **Kotlin Multiplatform**  | Shared application logic |
| **Compose Multiplatform** | Cross-platform UI        |
| **Kotlin Coroutines**     | Asynchronous programming |
| **Koin**                  | Dependency Injection     |
| **Room Multiplatform**    | Client-side persistence  |

### 🌐 BACKEND

| Technology      | Purpose               |
| --------------- | --------------------- |
| **Kotlin**      | Backend language      |
| **Ktor Server** | HTTP server and API   |
| **Ktor Client** | HTTP client           |
| **Exposed**     | Database access / ORM |
| **PostgreSQL**  | Relational database   |

### 🤖 AI & AUTOMATION

| Technology | Purpose                |
| ---------- | ---------------------- |
| **Koog**   | AI agent orchestration |
| **Kotlin** | Backend AI workflows   |

### 🏛️ ARCHITECTURE

* **Clean Architecture**
* **MVVM**
* **Repository Pattern**
* **Use Cases**
* **Dependency Injection**
* **Modular Architecture**
* **Shared Domain Logic**

---

## 🔄 KOTLIN-FIRST APPROACH

One of the main goals of WhereIsTheSolution is to explore how far a **Kotlin-first architecture** can go across an entire product.

```text
┌─────────────────────┐
│    Mobile Client    │
└──────────┬──────────┘
           │
           ▼
┌─────────────────────┐
│ Kotlin Multiplatform│
└──────────┬──────────┘
           │
           ▼
┌─────────────────────┐
│ Compose Multiplatform│
└──────────┬──────────┘
           │
           ▼
┌─────────────────────┐
│ Shared Domain/Data  │
└──────────┬──────────┘
           │
           ▼
┌─────────────────────┐
│    Ktor Client      │
└──────────┬──────────┘
           │
           ▼
┌─────────────────────┐
│    Ktor Server      │
└──────────┬──────────┘
           │
           ▼
┌─────────────────────┐
│      Exposed        │
└──────────┬──────────┘
           │
           ▼
┌─────────────────────┐
│     PostgreSQL      │
└─────────────────────┘
```

This architecture allows the project to share models, business rules, and infrastructure while maintaining clear boundaries between client and server responsibilities.

---

## 🗄️ DATA & PERSISTENCE

The project uses **Room Multiplatform** for client-side persistence and **Exposed** for backend database access.

The persistence layer is designed around clear separation between:

```text
UI
 ↓
ViewModel
 ↓
Use Case
 ↓
Repository
 ↓
Data Source
 ↓
Database / API
```

This structure keeps data access isolated from the presentation layer and makes the application easier to test and evolve.

---

## 🤖 AI-POWERED BACKEND

WhereIsTheSolution also explores the use of **Koog** for agent-based orchestration on the backend.

The objective is to experiment with AI agents as part of backend workflows while keeping the application's core business logic deterministic and maintainable.

Potential use cases include:

* Automated content processing
* Task orchestration
* Classification workflows
* Data enrichment
* Background automation
* Community-oriented analysis

> ⚠️ This part of the project is experimental and will evolve as the architecture and use cases become more mature.

---

## ✨ FEATURES

### 👤 COMMUNITY

* [ ] User authentication
* [ ] User profiles
* [ ] Community interactions
* [ ] Follow users
* [ ] User reputation

### 📍 REPORTING

* [ ] Create posts
* [ ] Upload images
* [ ] Add location
* [ ] Categorize reported problems
* [ ] Add descriptions
* [ ] Update report status

### 💬 SOCIAL

* [ ] Community feed
* [ ] Likes
* [ ] Comments
* [ ] Post discovery
* [ ] Sharing
* [ ] Notifications

### 🛡️ MODERATION

* [ ] Report content
* [ ] Moderation system
* [ ] Content classification
* [ ] Community guidelines
* [ ] Administrative tools

### 🤖 AI

* [ ] AI-assisted classification
* [ ] Automated content processing
* [ ] Backend agent workflows
* [ ] Intelligent task orchestration

---

## 🧠 ENGINEERING GOALS

WhereIsTheSolution is not only a product project.

It is also an engineering laboratory for exploring modern Kotlin development across the entire stack.

The project focuses on:

* **Kotlin Multiplatform**
* **Compose Multiplatform**
* **Clean Architecture**
* **Modular Kotlin applications**
* **Shared domain and data layers**
* **Ktor backend development**
* **Kotlin-first full-stack development**
* **Multiplatform persistence**
* **API design**
* **Dependency Injection**
* **AI agent orchestration**
* **Scalable application architecture**
* **Maintainable software design**

The project is intentionally being developed as a **real engineering experiment**, rather than simply as a UI prototype.

---

## 🚧 PROJECT STATUS

> 🟡 **ACTIVE DEVELOPMENT**

WhereIsTheSolution is currently under active development.

Architecture, features, infrastructure, and product decisions are continuously evolving as new requirements and engineering challenges emerge.

The project prioritizes:

**Maintainability · Modularity · Scalability · Testability · Developer Experience**

---

## 🎯 LONG-TERM VISION

The long-term vision is to create a platform where citizens can easily:

```text
DISCOVER
    ↓
REPORT
    ↓
DISCUSS
    ↓
SHARE
    ↓
FOLLOW
    ↓
TRACK
```

local problems and community issues.

The goal is not to replace existing social networks, but to create a **purpose-built environment for civic awareness and local community interaction**.

---

## 📚 WHY I BUILT THIS

I wanted to explore what it looks like to build a complete product using a **Kotlin-first technology stack**, from the mobile application all the way to the backend.

At the same time, the project addresses a problem I find relevant to local communities:

> **How can we make everyday problems more visible and easier for people to share and discuss?**

WhereIsTheSolution combines both goals into a single project:

**Build useful software while exploring modern Kotlin engineering across the entire stack.**

---

## 👨‍💻 AUTHOR

**Matheus Bento Vieira**

Software Engineer focused on:

`Kotlin` · `Android` · `Kotlin Multiplatform` · `Backend` · `C++`

[GitHub](https://github.com/Mouthlolses) · [LinkedIn](https://www.linkedin.com/in/matheusbentov/)

---

## 📄 LICENSE

This project is currently under active development.

License information will be added as the project reaches a stable release.
