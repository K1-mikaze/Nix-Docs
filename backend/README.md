## com/nixdocs 
│
├── Main.java              ← Entry point (Jetty/Undertow/etc. server)
│
├── config/                ← General configuration
│   └── AppConfig.java
|   |___AppCore.java
│
├── controller/            ← REST endpoints
│   └── UserController.java
│
├── model/                 ← Entities + DTOs (POJOs and transfer classes)
│   ├── User.java
│   └── UserDto.java
│
├── repository/            ← Data access (DAO/Repositories)
│   └── UserRepository.java
│
├── service/               ← Business logic
│   └── UserService.java
│
└── util/                  ← Utilities (JSON, validations, helpers)
    └── JsonUtil.java


🔄Simplifications made🔄: 

    api/ merged into model/
        DTOs (e.g., UserDto.java) can live alongside entities (User.java) because both represent "data."
        → This avoids having two separate folders for the same purpose.

    dao/ merged into repository/
        If you’re using raw JDBC, your UserRepository can also handle what UserDao did before.
        → This reduces confusion, since DAO and Repository often overlap.

    json/ merged into util/
        JSON serializers/deserializers are just helpers, so they fit nicely into util/.

    core/ removed
        The core folder usually ends up as a "catch-all" that only adds noise in small projects.
        → If you really need something like AppCore.java, put it in service/ or config/ instead.