rootProject.name = "my-service-demo"

// Common Module
include("common")

// Domain Module
include("domain")

// External Module
include("external-app")
include("external-app:event-consumer-worker")
findProject(":external-app:event-consumer-worker")?.name = "event-consumer-worker"
include("external-app:event-publisher-worker")
findProject(":external-app:event-publisher-worker")?.name = "event-publisher-worker"
include("external-app:batch")
findProject(":external-app:batch")?.name = "batch"

// Infrastructure Module
include("infrastructure")
include("infrastructure:jpa")
findProject(":infrastructure:jpa")?.name = "jpa"
include("infrastructure:mongodb")
findProject(":infrastructure:mongodb")?.name = "mongodb"
include("infrastructure:mongodb-reactive")
findProject(":infrastructure:mongodb-reactive")?.name = "mongodb-reactive"
include("infrastructure:redis")
findProject(":infrastructure:redis")?.name = "redis"
include("infrastructure:redis-reactive")
findProject(":infrastructure:redis-reactive")?.name = "redis-reactive"
include("infrastructure:retrofit2")
findProject(":infrastructure:retrofit2")?.name = "retrofit2"

// Internal Module
include("internal-app")
include("internal-app:admin")
findProject(":internal-app:admin")?.name = "admin"
include("internal-app:command")
findProject(":internal-app:command")?.name = "command"
include("internal-app:query")
findProject(":internal-app:query")?.name = "query"
include("internal-app:batch")
findProject(":internal-app:batch")?.name = "batch"
include("internal-app:event-worker")
findProject(":internal-app:event-worker")?.name = "event-worker"
