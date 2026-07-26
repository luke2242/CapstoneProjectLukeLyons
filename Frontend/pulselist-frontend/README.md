# PulseList

PulseList is a full-stack music tracking app where users can search music, view trending releases, and manage a personal listening list.

## Stack

- Frontend: React, TypeScript, Vite, Material UI, React Query
- Backend: Spring Boot, Java 21, Spring Security, JPA
- Auth: Firebase Authentication
- Data: PostgreSQL (H2 for backend tests)
- External API: Discogs

## Quick Start

### 1) Frontend

```bash
cd Frontend/pulselist-frontend
npm install
npm run dev
```

Frontend runs at `http://localhost:5173`.

Create [Frontend/pulselist-frontend/.env](Frontend/pulselist-frontend/.env):

```env
VITE_APIKEY=YOUR_FIREBASE_WEB_API_KEY
VITE_AUTHDOMAIN=YOUR_FIREBASE_AUTH_DOMAIN
VITE_PROJECTID=YOUR_FIREBASE_PROJECT_ID
VITE_STORAGEBUCKET=YOUR_FIREBASE_STORAGE_BUCKET
VITE_MESSAGINGSENDERID=YOUR_FIREBASE_MESSAGING_SENDER_ID
VITE_APPID=YOUR_FIREBASE_APP_ID

VITE_API_URL=YOUR_BACKEND_URL
VITE_PULSELIST_ADDUSERURL=YOUR_BACKEND_ADD_USER_URL
```

### 2) Backend

```bash
cd Pulselist
./mvnw spring-boot:run
```

Backend runs at `http://localhost:8081`.

Set these environment variables before running:

```env
DB_URL=jdbc:postgresql://localhost:5432/pulselist
DB_USERNAME=YOUR_DB_USERNAME
DB_PASSWORD=YOUR_DB_PASSWORD
FIREBASE_PROJECT_ID=YOUR_FIREBASE_PROJECT_ID
DISCOGS_TOKEN=YOUR_DISCOGS_TOKEN
DISCOGS_USER_AGENT=PulseList/1.0
CORS_ALLOWED_ORIGINS=http://localhost:5173
```

Also add Firebase admin key:

- [Pulselist/src/main/resources/firebase_private_key.json](Pulselist/src/main/resources/firebase_private_key.json)

## Main Routes

- `/` login
- `/signup` sign up
- `/home` home
- `/search` search releases
- `/trending` trending releases
- `/account` personal music list

## Key API Endpoints

- `POST /api/auth/sign-in`
- `POST /api/addUser`
- `GET /api/discogs/search?q={query}&type=release`
- `GET /api/discogs/trending?sortBy=year&count=50`
- `GET /api/user-music-list`
- `POST /api/user-music-list`
- `PATCH /api/user-music-list/{entryId}/status`
- `DELETE /api/user-music-list/{entryId}`

## Test and Build

Frontend:

```bash
cd Frontend/pulselist-frontend
npm run test -- --run
npm run build
```

Backend:

```bash
cd Pulselist
./mvnw test
./mvnw package
```

## Notes

- Do not commit real keys or credentials.
- This project is for educational/capstone use.

## Acknowledgements

- Thank you to Discogs for use of their API!
