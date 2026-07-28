# PulseList

PulseList is a full-stack music tracking app where users can search music, view trending releases, and manage a personal listening list.

## Stack

- Frontend: React, TypeScript, Vite, Material UI, React Query
- Backend: Spring Boot, Java 21, Spring Security, JPA, MapStruct
- Testing: JUnit, Mockito, Vitest, Jest
- Auth: Firebase Authentication
- Data: PostgreSQL (H2 for backend tests)
- External API: Discogs

## Key Features

- Firebase authentication
- Users can sign up and log in securely.
- Protected routes restrict private pages to authenticated users.

- Backend user sync on sign-in
- After Firebase login, the app syncs or creates the user in the Spring backend.

- Discogs-powered discovery
- Search releases by query.
- Browse trending releases.

- Personal music list management
- Add releases from search or trending into a personal list.
- Store release metadata such as title, artist, cover, and release ID.

- Listening status tracking
- Assign and update status per release:
  - Want to listen
  - Currently listening
  - Listened
  - Dropped

- List maintenance actions
- Filter list by status.
- Remove entries from your list.

- Dashboard-like account view
- See status counts and a quick overview of your listening queue.

- Responsive themed UI
- Unified Material UI branding and theme across pages and forms.

- Tested frontend flows
- Route protection and search behavior are covered by frontend tests.


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

### 2) Backend (local)

```bash
cd Pulselist
./mvnw spring-boot:run
```

Backend runs at `http://localhost:8081`.

Set these environment variables before running:

```env
DB_URL=YOUR_DB_URL
DB_USERNAME=YOUR_DB_USERNAME
DB_PASSWORD=YOUR_DB_PASSWORD
FIREBASE_PROJECT_ID=YOUR_FIREBASE_PROJECT_ID
DISCOGS_TOKEN=YOUR_DISCOGS_TOKEN
DISCOGS_USER_AGENT=YOUR_USER_AGENT_DISCOGS
CORS_ALLOWED_ORIGINS=http://localhost:5173
```

Also add Firebase admin key. For Render, set one of these secrets:

- `FIREBASE_PRIVATE_KEY_JSON` with the full service-account JSON as a string
- `FIREBASE_PRIVATE_KEY_PATH` with the path to a mounted secret file

The backend will still fall back to [Pulselist/src/main/resources/firebase_private_key.json](/home/l/Desktop/CapstoneProjectLukeLyons/Pulselist/src/main/resources/firebase_private_key.json) for local development.

## Backend Docker (Render)

- The backend includes a Dockerfile for deployment on Render.


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

- Render is running back-end on hobby tier, which means Spring boot web service will spin down during inactivity. This will cause the application to not function as intended.
  If you need to use PulseList web app at anytime, please contact me at my NUIG student email and I will get back to you as soon as possible.
- Do not commit real keys or credentials.
- This project is for educational/capstone use.
- This application relies on Discogs API and may not function as intended if Discogs goes down or rate limits are exceeded.

## Acknowledgements

- Thank you to Discogs for use of their API!
