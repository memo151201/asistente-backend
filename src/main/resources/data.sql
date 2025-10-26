-- ============================================
-- DATOS FALTANTES - Completar Subtemas y Preguntas
-- ============================================

-- ============================================
-- SUBTEMAS FALTANTES
-- ============================================

-- ============================================
-- TEMA 3: Funciones y Métodos (tema_id: 3)
-- ============================================
INSERT INTO subtemas (nombre, descripcion, contenido, orden, tema_id) VALUES
('Definición de Funciones', 'Cómo crear funciones en Java', 'Una función (método) en Java se define con: tipoRetorno nombreMetodo(parametros) { }. Por ejemplo: public int sumar(int a, int b) { return a + b; }', 1, 3),
('Parámetros y Argumentos', 'Paso de valores a funciones', 'Los parámetros son variables que recibe la función. Los argumentos son los valores que se pasan al llamar la función. Ejemplo: void saludar(String nombre) { System.out.println("Hola " + nombre); }', 2, 3),
('Retorno de Valores', 'Uso de return en funciones', 'La palabra clave return devuelve un valor desde una función. Si el método es void, no retorna nada. Ejemplo: public double calcularPromedio(double a, double b) { return (a + b) / 2; }', 3, 3);

-- ============================================
-- TEMA 5: Herencia (tema_id: 5)
-- ============================================
INSERT INTO subtemas (nombre, descripcion, contenido, orden, tema_id) VALUES
('Concepto de Herencia', 'Qué es la herencia en POO', 'La herencia permite crear nuevas clases basadas en clases existentes, reutilizando código. Se usa la palabra clave "extends". Ejemplo: class Perro extends Animal { }', 1, 5),
('Palabra Clave extends', 'Sintaxis de herencia', 'Para heredar de una clase se usa extends: class ClaseHija extends ClasePadre. La clase hija hereda atributos y métodos de la clase padre.', 2, 5),
('Método super()', 'Llamar al constructor del padre', 'super() se usa para llamar al constructor de la clase padre. Debe ser la primera línea del constructor hijo. Ejemplo: super(parametros);', 3, 5);

-- ============================================
-- TEMA 6: Polimorfismo (tema_id: 6)
-- ============================================
INSERT INTO subtemas (nombre, descripcion, contenido, orden, tema_id) VALUES
('Sobrecarga de Métodos', 'Múltiples métodos con el mismo nombre', 'La sobrecarga permite tener varios métodos con el mismo nombre pero diferentes parámetros. Ejemplo: void imprimir(int x) { } y void imprimir(String s) { }', 1, 6),
('Sobreescritura de Métodos', 'Redefinir métodos heredados', 'La sobreescritura permite redefinir un método heredado usando @Override. La firma debe ser idéntica. Ejemplo: @Override public void hacerSonido() { }', 2, 6),
('Polimorfismo en Acción', 'Uso práctico del polimorfismo', 'El polimorfismo permite tratar objetos de diferentes clases de manera uniforme. Ejemplo: Animal a = new Perro(); a.hacerSonido(); // Llama al método de Perro', 3, 6);

-- ============================================
-- TEMA 7: Listas Enlazadas (tema_id: 7)
-- ============================================
INSERT INTO subtemas (nombre, descripcion, contenido, orden, tema_id) VALUES
('Concepto de Lista Enlazada', 'Estructura de datos dinámica', 'Una lista enlazada es una secuencia de nodos donde cada nodo contiene datos y una referencia al siguiente nodo. No requiere memoria contigua.', 1, 7),
('Nodo de Lista', 'Estructura básica del nodo', 'Un nodo tiene dos partes: el dato (valor) y la referencia al siguiente nodo. class Nodo { int dato; Nodo siguiente; }', 2, 7),
('Operaciones Básicas', 'Insertar, eliminar, buscar', 'Las operaciones principales son: insertar al inicio/final, eliminar un nodo, buscar un elemento, y recorrer la lista.', 3, 7);

-- ============================================
-- TEMA 8: Pilas y Colas (tema_id: 8)
-- ============================================
INSERT INTO subtemas (nombre, descripcion, contenido, orden, tema_id) VALUES
('Estructura de Pila (Stack)', 'LIFO - Last In First Out', 'Una pila es una estructura donde el último elemento en entrar es el primero en salir. Operaciones principales: push (apilar) y pop (desapilar).', 1, 8),
('Estructura de Cola (Queue)', 'FIFO - First In First Out', 'Una cola es una estructura donde el primer elemento en entrar es el primero en salir. Operaciones principales: enqueue (encolar) y dequeue (desencolar).', 2, 8),
('Implementación con Arrays', 'Pilas y colas con arreglos', 'Se pueden implementar pilas y colas usando arrays, controlando índices para el tope de la pila o el frente y final de la cola.', 3, 8);

-- ============================================
-- TEMA 9: Árboles Binarios (tema_id: 9)
-- ============================================
INSERT INTO subtemas (nombre, descripcion, contenido, orden, tema_id) VALUES
('Concepto de Árbol Binario', 'Estructura jerárquica de datos', 'Un árbol binario es una estructura donde cada nodo tiene como máximo dos hijos: izquierdo y derecho. El primer nodo es la raíz.', 1, 9),
('Recorridos de Árbol', 'Inorden, Preorden, Postorden', 'Los recorridos principales son: Inorden (izq-raíz-der), Preorden (raíz-izq-der), Postorden (izq-der-raíz).', 2, 9),
('Árbol Binario de Búsqueda', 'BST - Binary Search Tree', 'Un BST mantiene la propiedad: nodos izquierdos < raíz < nodos derechos. Permite búsqueda eficiente en O(log n).', 3, 9);

-- ============================================
-- PREGUNTAS FALTANTES
-- ============================================

-- ============================================
-- PREGUNTAS: Tema 3 - Funciones (subtemas 10, 11, 12)
-- ============================================
INSERT INTO preguntas (enunciado, opcion_a, opcion_b, opcion_c, opcion_d, respuesta_correcta, explicacion, nivel_dificultad, activa, subtema_id) VALUES
-- Subtema 10: Definición de Funciones
('¿Cuál es la sintaxis correcta para definir un método en Java?', 'function int sumar() { }', 'public int sumar() { }', 'def sumar(): int', 'method int sumar() { }', 'B', 'En Java, los métodos se definen con: modificador tipoRetorno nombreMetodo(parametros) { cuerpo }', 'FACIL', 1, 10),
('¿Qué indica "void" en la definición de un método?', 'Que el método es vacío', 'Que no retorna ningún valor', 'Que no tiene parámetros', 'Que el método es privado', 'B', 'void indica que el método no retorna ningún valor.', 'FACIL', 1, 10),

-- Subtema 11: Parámetros y Argumentos
('¿Cuál es la diferencia entre parámetro y argumento?', 'No hay diferencia', 'Parámetro es en la definición, argumento al llamar', 'Argumento es en la definición, parámetro al llamar', 'Los argumentos son opcionales', 'B', 'Los parámetros están en la definición del método, los argumentos son los valores que se pasan al llamarlo.', 'MEDIO', 1, 11),
('¿Cómo se pasan múltiples parámetros a un método?', 'Separados por comas', 'Separados por punto y coma', 'En un array', 'Uno por línea', 'A', 'Los parámetros se separan con comas: public void metodo(int a, String b, double c)', 'FACIL', 1, 11),

-- Subtema 12: Retorno de Valores
('¿Qué palabra clave se usa para retornar un valor?', 'give', 'return', 'send', 'output', 'B', 'La palabra clave return se usa para devolver un valor desde un método.', 'FACIL', 1, 12),
('Un método con tipo de retorno int debe obligatoriamente...', 'Tener parámetros int', 'Retornar un valor int', 'Llamarse "int"', 'Ser público', 'B', 'Si un método declara un tipo de retorno, debe retornar un valor de ese tipo.', 'MEDIO', 1, 12);

-- ============================================
-- PREGUNTAS: Tema 5 - Herencia (subtemas 13, 14, 15)
-- ============================================
INSERT INTO preguntas (enunciado, opcion_a, opcion_b, opcion_c, opcion_d, respuesta_correcta, explicacion, nivel_dificultad, activa, subtema_id) VALUES
-- Subtema 13: Concepto de Herencia
('¿Qué permite la herencia en POO?', 'Crear métodos', 'Reutilizar código de otra clase', 'Ocultar datos', 'Ejecutar múltiples hilos', 'B', 'La herencia permite que una clase herede atributos y métodos de otra clase.', 'FACIL', 1, 13),
('Una clase que hereda de otra se llama...', 'Clase padre', 'Clase hija o subclase', 'Clase abstracta', 'Clase principal', 'B', 'La clase que hereda se llama clase hija, subclase o clase derivada.', 'FACIL', 1, 13),

-- Subtema 14: extends
('¿Qué palabra clave se usa para heredar en Java?', 'inherits', 'extends', 'implements', 'from', 'B', 'En Java se usa "extends" para indicar que una clase hereda de otra.', 'FACIL', 1, 14),
('¿Cuántas clases puede extender una clase en Java?', 'Ninguna', 'Solo una', 'Dos', 'Ilimitadas', 'B', 'Java solo permite herencia simple: una clase puede extender solo una clase.', 'MEDIO', 1, 14),

-- Subtema 15: super()
('¿Para qué se usa super()?', 'Para crear objetos', 'Para llamar al constructor del padre', 'Para llamar métodos estáticos', 'Para cerrar el programa', 'B', 'super() llama al constructor de la clase padre y debe ser la primera línea del constructor hijo.', 'MEDIO', 1, 15),
('¿Dónde debe ubicarse super() en el constructor?', 'Al final', 'En cualquier lugar', 'Como primera línea', 'No importa', 'C', 'super() debe ser la primera sentencia en el constructor de la clase hija.', 'DIFICIL', 1, 15);

-- ============================================
-- PREGUNTAS: Tema 6 - Polimorfismo (subtemas 16, 17, 18)
-- ============================================
INSERT INTO preguntas (enunciado, opcion_a, opcion_b, opcion_c, opcion_d, respuesta_correcta, explicacion, nivel_dificultad, activa, subtema_id) VALUES
-- Subtema 16: Sobrecarga
('La sobrecarga de métodos permite...', 'Métodos con mismo nombre pero diferentes parámetros', 'Métodos privados', 'Herencia múltiple', 'Crear interfaces', 'A', 'La sobrecarga permite definir varios métodos con el mismo nombre pero diferentes listas de parámetros.', 'MEDIO', 1, 16),
('¿Qué debe ser diferente en la sobrecarga?', 'El nombre del método', 'El tipo de retorno', 'Los parámetros', 'El modificador de acceso', 'C', 'Para sobrecarga, la lista de parámetros debe ser diferente (tipo, cantidad u orden).', 'MEDIO', 1, 16),

-- Subtema 17: Sobreescritura
('¿Qué anotación se usa para sobreescribir un método?', '@Overload', '@Override', '@Inherit', '@Redefine', 'B', '@Override indica que estás sobreescribiendo un método de la clase padre.', 'FACIL', 1, 17),
('En la sobreescritura, la firma del método debe ser...', 'Diferente', 'Idéntica', 'Más larga', 'Opcional', 'B', 'Para sobreescribir, la firma (nombre, parámetros, tipo retorno) debe ser idéntica.', 'MEDIO', 1, 17),

-- Subtema 18: Polimorfismo en Acción
('¿Qué es el polimorfismo?', 'Herencia múltiple', 'Tratar objetos de diferentes tipos de manera uniforme', 'Ocultar datos', 'Crear clases abstractas', 'B', 'El polimorfismo permite que un objeto pueda tomar muchas formas y ser tratado de manera uniforme.', 'MEDIO', 1, 18),
('Animal a = new Perro(); ¿Qué tipo de polimorfismo es?', 'Sobrecarga', 'Polimorfismo de tiempo de compilación', 'Polimorfismo de tiempo de ejecución', 'No es polimorfismo', 'C', 'Esto es polimorfismo en tiempo de ejecución (runtime polymorphism) o vinculación dinámica.', 'DIFICIL', 1, 18);

-- ============================================
-- PREGUNTAS: Tema 7 - Listas (subtemas 19, 20, 21)
-- ============================================
INSERT INTO preguntas (enunciado, opcion_a, opcion_b, opcion_c, opcion_d, respuesta_correcta, explicacion, nivel_dificultad, activa, subtema_id) VALUES
-- Subtema 19: Concepto
('Una lista enlazada está formada por...', 'Arrays contiguos', 'Nodos enlazados', 'Pilas', 'Árboles', 'B', 'Una lista enlazada es una secuencia de nodos donde cada nodo apunta al siguiente.', 'FACIL', 1, 19),
('¿Qué ventaja tiene una lista enlazada sobre un array?', 'Acceso directo por índice', 'Tamaño dinámico', 'Menos memoria', 'Más rápida', 'B', 'Las listas enlazadas pueden crecer o reducirse dinámicamente sin necesidad de memoria contigua.', 'MEDIO', 1, 19),

-- Subtema 20: Nodo
('¿Qué contiene un nodo de lista enlazada simple?', 'Solo el dato', 'El dato y referencia al siguiente', 'Dos referencias', 'Un índice', 'B', 'Un nodo simple contiene el dato y una referencia al siguiente nodo.', 'MEDIO', 1, 20),
('¿Cómo se indica el final de una lista enlazada?', 'Con -1', 'Con null', 'Con 0', 'No se indica', 'B', 'El último nodo tiene su referencia "siguiente" en null, indicando el fin de la lista.', 'FACIL', 1, 20),

-- Subtema 21: Operaciones
('¿Cuál es la complejidad de insertar al inicio de una lista enlazada?', 'O(1)', 'O(n)', 'O(log n)', 'O(n²)', 'A', 'Insertar al inicio es O(1) porque solo se modifica la cabeza de la lista.', 'DIFICIL', 1, 21),
('Para buscar un elemento en una lista enlazada, ¿qué se debe hacer?', 'Acceso directo por índice', 'Recorrer nodo por nodo', 'Usar búsqueda binaria', 'Ordenar primero', 'B', 'En listas enlazadas se debe recorrer secuencialmente nodo por nodo para buscar.', 'MEDIO', 1, 21);

-- ============================================
-- PREGUNTAS: Tema 8 - Pilas y Colas (subtemas 22, 23, 24)
-- ============================================
INSERT INTO preguntas (enunciado, opcion_a, opcion_b, opcion_c, opcion_d, respuesta_correcta, explicacion, nivel_dificultad, activa, subtema_id) VALUES
-- Subtema 22: Pila
('¿Qué significa LIFO?', 'Last Input First Output', 'Last In First Out', 'List In File Out', 'Large In Fast Out', 'B', 'LIFO significa Last In First Out: el último en entrar es el primero en salir.', 'FACIL', 1, 22),
('¿Qué operación agrega un elemento a una pila?', 'enqueue', 'push', 'insert', 'add', 'B', 'push es la operación para agregar (apilar) un elemento en una pila.', 'FACIL', 1, 22),

-- Subtema 23: Cola
('¿Qué significa FIFO?', 'Fast In Fast Out', 'First In First Out', 'File In File Out', 'Front In Final Out', 'B', 'FIFO significa First In First Out: el primero en entrar es el primero en salir.', 'FACIL', 1, 23),
('¿Qué operación agrega un elemento a una cola?', 'push', 'pop', 'enqueue', 'peek', 'C', 'enqueue es la operación para agregar (encolar) un elemento al final de una cola.', 'MEDIO', 1, 23),

-- Subtema 24: Implementación
('¿Qué se necesita para implementar una pila con array?', 'Un índice para el tope', 'Dos índices', 'Un puntero', 'Una lista', 'A', 'Para implementar una pila con array se necesita un índice que marque el tope de la pila.', 'MEDIO', 1, 24),
('En una cola circular, ¿qué se hace cuando se llega al final del array?', 'Error', 'Se redimensiona', 'Se vuelve al inicio', 'Se detiene', 'C', 'En una cola circular, cuando se llega al final se vuelve al inicio (módulo del tamaño).', 'DIFICIL', 1, 24);

-- ============================================
-- PREGUNTAS: Tema 9 - Árboles (subtemas 25, 26, 27)
-- ============================================
INSERT INTO preguntas (enunciado, opcion_a, opcion_b, opcion_c, opcion_d, respuesta_correcta, explicacion, nivel_dificultad, activa, subtema_id) VALUES
-- Subtema 25: Concepto
('¿Cuántos hijos puede tener un nodo en un árbol binario?', 'Uno', 'Dos como máximo', 'Tres', 'Ilimitados', 'B', 'En un árbol binario, cada nodo tiene como máximo dos hijos: izquierdo y derecho.', 'FACIL', 1, 25),
('El nodo del cual parten todos los demás se llama...', 'Hoja', 'Raíz', 'Rama', 'Padre', 'B', 'La raíz es el primer nodo del árbol, del cual parten todos los demás.', 'FACIL', 1, 25),

-- Subtema 26: Recorridos
('En recorrido Inorden, ¿cuál es el orden?', 'Raíz-Izq-Der', 'Izq-Raíz-Der', 'Izq-Der-Raíz', 'Der-Raíz-Izq', 'B', 'Inorden: se visita primero el subárbol izquierdo, luego la raíz, luego el derecho.', 'MEDIO', 1, 26),
('¿Qué recorrido visita la raíz primero?', 'Inorden', 'Postorden', 'Preorden', 'Nivel por nivel', 'C', 'Preorden visita primero la raíz, luego izquierdo, luego derecho.', 'MEDIO', 1, 26),

-- Subtema 27: BST
('En un BST, los nodos del subárbol izquierdo son...', 'Mayores que la raíz', 'Menores que la raíz', 'Iguales a la raíz', 'Aleatorios', 'B', 'En un BST (Binary Search Tree), todos los nodos izquierdos son menores que la raíz.', 'MEDIO', 1, 27),
('¿Cuál es la complejidad de búsqueda en un BST balanceado?', 'O(1)', 'O(n)', 'O(log n)', 'O(n²)', 'C', 'En un BST balanceado, la búsqueda tiene complejidad O(log n).', 'DIFICIL', 1, 27);

