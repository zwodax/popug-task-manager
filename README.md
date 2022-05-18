# Event Storming diagram

[Ссылка в Lucidchart](https://lucid.app/documents/view/38be650d-6e10-48e0-8c7b-4f0629c0ad9e)

В результате выделено 4 сервиса:
1. Сервис авторизации и регистрации аккаунтов (Auth)
2. Сервис управления задачами (Task Tracker)
3. Сервис управления счетами (Accounting)
4. Сервис аналитики (Analytics)

Бизнес взаимодействия между сервисами и внутри одного сервиса отражены
в виде стрелок. Асинхронные взаимодействия - пунктирная стрелка, 
синхронные - обычная стрелка.

Все межсервисное взаимодействие асинхронное, 
за исключением запросов аналитики из сервиса аккаунтинга.

В схеме отсутствуют стрелки взаимодействия продьюсеров и консьюмеров
CUD событий, они выписаны ниже.

## CUD events

Producer | Event |Consumers | Data Model |
------------- |----------------| ---------------- | ----------------
Auth  | Accounts.created   | Task Tracker, Accounting, Analytics | Account(id, role, mail)
TaskTraker | Task.Created   | Accounting, Analytics | Task (id,description,status,assignee_id)
Accounting | Accounting.TaskPriceCreated | Analytics | TaskPrice(id,task_id,fine, fee)
Accounting | Accounting.OperationCreated | Analytics | Operation(id,task_id,account_id,timestamp,operation_type)


## Business Events

Producer | Event | Consumers  | Data Model |
------------- |----------------|------------| ----------------
TaskTraker  | Task.Created   | Accounting | Task (id,description,status,assignee_id)
TaskTraker | Task.Completed   | Accounting | task_id
TaskTraker | Task.Assigned | Accounting | task_id
Accounting | Accounting.PaidOut | Accounting | account_id, sum
Accounting | Accounting.TaskPriceCreated | Accounting | TaskPrice(id,task_id,fine, fee)
