# Rotisería Chatbot - Backend

## Descripción

Este proyecto es un backend desarrollado en Java con Spring Boot cuyo objetivo es automatizar la gestión de pedidos de una rotisería, pensado para integrarse con canales como WhatsApp.

Permite modelar el flujo completo de pedidos, desde la selección de productos hasta la gestión de estados y el registro de historial.

---

## Funcionalidades principales

* Gestión de productos (menú)
* Creación de pedidos
* Asociación de múltiples ítems a un pedido
* Cálculo automático del total
* Gestión de estados del pedido
* Registro de historial de cambios de estado
* Manejo automático de timestamps

---

## Modelo de dominio

### Cliente

Representa al cliente que realiza pedidos.

### Producto

Representa los productos disponibles.

Campos principales:

* nombre
* descripción
* precio
* disponible
* categoría

### Pedido

Entidad central del sistema.

Responsabilidades:

* asociarse a un cliente
* contener múltiples ítems
* calcular el total
* gestionar el estado
* registrar cambios de estado

### ItemPedido

Representa un producto dentro de un pedido.

Incluye:

* producto
* cantidad
* precio unitario (snapshot al momento del pedido)
* subtotal

### HistorialPedido

Registra cambios de estado del pedido a lo largo del tiempo.

---

## Tecnologías utilizadas

* Java
* Spring Boot
* JPA / Hibernate
* PostgreSQL
* Lombok

---

## Estructura del proyecto

```
src/main/java/com/rotiseria/chatbot/

model/
 ├── Cliente.java
 ├── Producto.java
 ├── Pedido.java
 ├── ItemPedido.java
 └── HistorialPedido.java

model/enums/
 └── EstadoPedido.java
```

---

## Lógica de negocio

### Agregar ítems a un pedido

Se mantiene la consistencia de la relación y se recalcula el total:

```java
public void agregarItem(ItemPedido item) {
    item.setPedido(this);
    this.items.add(item);
    recalcularTotal();
}
```

### Recalcular total

El total se obtiene sumando los subtotales de los ítems:

```java
public void recalcularTotal() {
    this.total = items.stream()
            .map(ItemPedido::getSubtotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
}
```

### Cambio de estado

Cada cambio de estado se registra en el historial:

```java
public void cambiarEstado(EstadoPedido nuevoEstado, String nota) {
    HistorialPedido registro = HistorialPedido.builder()
            .pedido(this)
            .estadoAnterior(this.estado)
            .estadoNuevo(nuevoEstado)
            .nota(nota)
            .build();

    this.historial.add(registro);
    this.estado = nuevoEstado;
}
```

---

## Persistencia y relaciones

* Cliente → Pedido (uno a muchos)
* Pedido → ItemPedido (uno a muchos)
* Pedido → HistorialPedido (uno a muchos)
* ItemPedido → Producto (muchos a uno)

Se utilizan:

* `cascade` para propagación de operaciones
* `orphanRemoval` para eliminación automática de ítems desvinculados
* `FetchType.LAZY` para optimizar la carga de datos

---

## Timestamps

Se gestionan automáticamente mediante anotaciones:

* `@CreationTimestamp`: fecha de creación
* `@UpdateTimestamp`: última actualización

---

## Estado del proyecto

Actualmente el proyecto incluye:

* Modelo de dominio completo
* Lógica de negocio en entidades

Próximos pasos:

* Implementación de repositorios (Spring Data JPA)
* Capa de servicios
* Exposición de API REST
* Integración con API de WhatsApp

---

## Objetivo

Construir una base sólida para un sistema que permita a negocios gastronómicos automatizar la gestión de pedidos y mejorar la eficiencia operativa.
