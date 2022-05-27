# My Service Demo

### 왜 이벤트 아키텍처를 구현해야하는지 항상 고민하자!

## 프로젝트 구조

```
├── common
├── domain
├── external-app
│   ├── batch
│   └── event-consumer-worker
│   └── event-publisher-worker
├── infrastructure
│   ├── jpa
│   ├── mongodb-reactive
│   ├── mongodb
│   ├── redis-reactive
│   ├── redis
│   └── retrofit2
└── internal-app
    ├── admin
    ├── batch
    ├── command
    ├── event-worker
    └── query
```

## 아키텍쳐

<img width="1888" alt="image" src="https://user-images.githubusercontent.com/23515771/166139292-a11321dc-98d5-4e90-8d0e-2942277fae62.png">

### Internal - Admin 모듈

- `common`
- `domain`
- `infrastructure:jpa`

### Internal - Batch 모듈

- `common`
- `domain`
- `infrastructure:jpa`
- `infrastructure:monogodb`
- `infrastructure:redis`

### Internal - Command 모듈

- `common`
- `domain`
- `infrastructure:jpa`

### Internal - Event Worker 모듈

- `common`
- `domain`
- `infrastructure:jpa`
- `infrastructure:monogodb`
- `infrastructure:redis`
- `infrastructure:retrofit2`

### Internal - Query 모듈

- `common`
- `domain`
- `infrastructure:monogodb-reactive`
- `infrastructure:redis-reactive`

### Infrastructure - Jpa 모듈

- `domain`

### Infrastructure - MongoDB 모듈

- `common`
- `domain`

### Infrastructure - MongoDB Reactive 모듈

- `common`
- `domain`

### Infrastructure - Redis 모듈

- `common`
- `domain`

### Infrastructure - Redis Reactive 모듈

- `common`
- `domain`

### Infrastructure - Retrofit2 모듈

- `common`
- `domain`
