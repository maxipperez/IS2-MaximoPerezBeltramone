# Tres en línea con React

Mini proyecto realizado para practicar los contenidos de **Introducción a React**.

Está inspirado en la idea del proyecto 02 (Tic Tac Toe) del repositorio de Midudev, pero fue implementado desde cero con una interfaz y código propios.

## Conceptos aplicados

- Componentes reutilizables (`Square` y `ScoreBoard`).
- Props para comunicar componentes.
- Estado con `useState`.
- Eventos `onClick`.
- Renderizado condicional de turno, ganador o empate.
- Renderizado de listas con `map` y uso de `key`.
- Actualización inmutable del tablero y del marcador.

## Ejecución

```bash
npm install
npm run dev
```

## Funcionalidades

- Dos jugadores: X y O.
- Detección de las ocho combinaciones ganadoras.
- Detección de empate.
- Marcador acumulado de rondas ganadas y empatadas.
- Botones para comenzar otra ronda o restablecer el marcador completo.
