import { createRouter, createWebHistory } from "vue-router";
import functionPoint from "../views/functionPoint.vue";
import classDiagram from "../views/classDiagram.vue";
import code from "../views/code.vue";
import controlFlow from "../views/controlFlow.vue";
import useCase from "../views/useCase.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "functionPoint",
      component: functionPoint,
    },
    {
      path: "/classDiagram",
      name: "classDiagram",
      component: classDiagram,
    },
    {
      path: "/controlFlow",
      name: "controlFlow",
      component: controlFlow,
    },
    {
      path: "/code",
      name: "code",
      component: code,
    },
    {
      path: "/useCase",
      name: "useCase",
      component: useCase,
    },
  ],
});

export default router;
