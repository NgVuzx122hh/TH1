# Patient Service - Buoi thuc hanh 1

RESTful service quan ly benh nhan bang Spring Boot. Du lieu duoc luu tam trong bo nho; khoi dong lai ung dung se xoa du lieu. Project khong su dung database, Eureka, Gateway, Docker hay Kafka.

## Yeu cau

- Java 17 tro len
- Maven 3.6 tro len
- Postman (tuy chon, de goi thu API)

## Chay ung dung

```powershell
mvn spring-boot:run
```

Ung dung mac dinh chay tai `http://localhost:8080`. Chay test:

```powershell
mvn test
```

Co the import `Patient-Service.postman_collection.json` vao Postman de goi thu API.

## API

Tat ca endpoint quan ly benh nhan co tien to `/api/patients`.

| Method | Endpoint | Mo ta | Thanh cong |
|---|---|---|---|
| GET | `/api/patients/hello` | Kiem tra Patient Service | 200 |
| GET | `/api/patients/info` | Thong tin service | 200 |
| GET | `/api/patients` | Lay danh sach | 200 |
| GET | `/api/patients/{id}` | Lay mot benh nhan | 200, 404 neu khong co |
| POST | `/api/patients` | Tao benh nhan | 201, 400 neu du lieu khong hop le |
| PUT | `/api/patients/{id}` | Cap nhat benh nhan | 200, 400 neu du lieu khong hop le, 404 neu khong co |
| DELETE | `/api/patients/{id}` | Xoa benh nhan | 200, 404 neu khong co |
| GET | `/api/patients/search?name=An` | Tim theo ten (khong phan biet hoa thuong) | 200 |

### Vi du tao benh nhan

Gui `POST /api/patients` voi `Content-Type: application/json`:

```json
{
  "fullName": "Nguyen Van An",
  "dateOfBirth": "2003-05-12",
  "gender": "Male",
  "phone": "0901234567",
  "address": "Ha Noi"
}
```

`id` duoc tao boi service. Cac truong `fullName` va `phone` khong duoc de trong; `dateOfBirth` phai co dinh dang `yyyy-MM-dd`.

Loi tra ve JSON co cau truc:

```json
{
  "status": 404,
  "message": "Patient not found"
}
```

## Cau truc

```text
src/main/java/vn/edu/patientservice/
  PatientServiceApplication.java
  controller/PatientController.java
  exception/ApiExceptionHandler.java
  exception/PatientNotFoundException.java
  model/ApiError.java
  model/Patient.java
  model/PatientRequest.java
  service/PatientService.java
src/test/java/vn/edu/patientservice/
  controller/PatientControllerTest.java
```
