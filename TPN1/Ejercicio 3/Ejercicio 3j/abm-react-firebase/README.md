# ABM React con Firebase

Mini proyecto que reemplaza la guía audiovisual del ejercicio 3j. Implementa un ABM (alta, baja y modificación) de productos con React, Vite y Cloud Firestore. La lista se actualiza en tiempo real mediante `onSnapshot`.

## Requisitos

- Node.js 18 o superior.
- Una cuenta/proyecto de Firebase.

## Puesta en marcha

1. Abrir una terminal en esta carpeta y ejecutar `npm install`.
2. Copiar `.env.example` a un archivo llamado `.env`.
3. En [Firebase Console](https://console.firebase.google.com/), crear un proyecto, registrar una aplicación web y copiar su configuración al archivo `.env`.
4. En Firebase Console, crear una base de datos **Cloud Firestore** en modo de prueba.
5. Ejecutar `npm run dev` y abrir la dirección indicada por Vite.

> Las credenciales de Firebase no son secretas, pero el archivo `.env` se ignora para evitar subir configuraciones específicas. Para una entrega real deben configurarse reglas de seguridad y autenticación.


