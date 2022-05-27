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
