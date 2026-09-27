import React from "react";
import { render, screen } from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import Contador from "./EjemploContador.jsx";

test("muestra el valor inicial en 0", () => {
  render(<Contador />);

  expect(screen.getByText("Valor: 0")).toBeInTheDocument();
});

test("suma 1 cada vez que se presiona Sumar", async () => {
  render(<Contador />);

  await userEvent.click(screen.getByRole("button", { name: "Sumar" }));
  await userEvent.click(screen.getByRole("button", { name: "Sumar" }));

  expect(screen.getByText("Valor: 2")).toBeInTheDocument();
});

test("el botón Restar está desactivado cuando el valor es 0", () => {
  render(<Contador />);

  expect(screen.getByRole("button", { name: "Restar" })).toBeDisabled();
});

test("Reiniciar vuelve el valor a 0", async () => {
  render(<Contador />);

  await userEvent.click(screen.getByRole("button", { name: "Sumar" }));
  await userEvent.click(screen.getByRole("button", { name: "Reiniciar" }));

  expect(screen.getByText("Valor: 0")).toBeInTheDocument();
});
