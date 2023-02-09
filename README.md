api 명세서

| Method | URL | Request | Response |
| --- | --- | --- | --- |
| GET | /api/blogs |  | {
"result": [
{
"id": 3,
"username": "a",
"title": "c",
"contents": "d"
},
{
"id": 2,
"username": "a",
"title": "c",
"contents": "d"
},
{
"id": 1,
"username": "a",
"title": "c",
"contents": "d"
}
],
"message": "조회 성공"
} |
| GET | /api/getone/{id} |  | {
"result": {
"id": 1,
"username": "a",
"title": "c",
"contents": "d"
},
"message": "조회 성공"
} |
| POST | /api/blogs | {
"username":"a",
"password":"b",
"title":"c",
"contents":"d"
} | {
"id": 3,
"username": "a",
"title": "c",
"contents": "d"
} |
| PUT | /api/blogs/{id} | {
"username":"aaaa1",
"password":"b",
"title":"3aaa3",
"contents":"44"
} | {
"result": {
"id": 1,
"username": "aaaa1",
"title": "3aaa3",
"contents": "44"
},
"message": "수정 성공"
} |
| DELETE | /api/blogs/{id} | {"password":"bbb"} | {
"result": {
"id": 1,
"username": "aaaa1",
"title": "3aaa3",
"contents": "44"
},
"message": "삭제 성공"
} |
