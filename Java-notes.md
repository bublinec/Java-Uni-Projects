# Java programming:
-------------------------------------------------------------------------------

- 20% top 5/8 labs - microsoft teams (browser) -log in with student account
- 20% lab exam
- 60% one hour exam

- software: Java OpenJDK 15/14, Eclipse IDE
- at least show up on the labs - attendance is taken (check)

About Java:
- platform neutral
- strongly OOP
- used widely server side
- behind Android
- syntax similiar to C/C++: 
	- semicolons;
	- curly brackets
	- comments // or /*  */
- 
- JShell
- JDK is commercial verison by oracle, openJDK is opensource

-------------------------------------------------------------------------------

# Basics
 
 - primitive type - stores actual value [int,  float, char, bool]
 - non-primitive types - store memory location - pointer [string, arr, object]
 - strings act like primitive (unmutable) but are not technically
 - compare strings - .equals() 
 - compare arrays - array.equals()
 - statically typed language - variable can only hold values of a specific type (unlike python), we need to declare type
 	-> we would need to conver it (excpet for widening a number - implicit conversion)
 - float/integer division depends on the type
 - declaration - assinging a identifier (name) to a memory location 
 	- follow the conventions:
	- CamelCase, ALLCAPS constants, lower variables, upper objects
 - initialisation - storing variable there
 - overloading - functions with same name, different param types
 - in method (or other scope), I cannot change the pointer passed, but I can change the content of array/str/obj itself 
 	
 # Objects Intro
 - state	 -> props
 - behaviour -> methods
 
 Reasons:
 - modularity
 - reusability
 - easier to code, test
 - encapsulation (blac boxex) / abstraction
 	- each isntace has data and behaviour associated with it
 	- module is done and tested -> I can trust it and use it, without thinking how it works -> I can contrnetrate on other things
 	
 - class - blueprint - type
 - object- instance of class
 
## Constructors 
- method with the same name as class, no return type - set up initial values/state
- if not specified - default no-args constructor - assign default values specific to each type (null/0)
- objects commonly have more constructors with same names but different params (overloading)
	- cal the first one in the second - to make it dry
- in subclass super() is called implicitly, if new constructor is not implemented
	- if there is no basic constructor on the parent, we get error
	-> include super wehn defining objects
 
## Visibility
 - WE DON'T WANT TO DIRECTLY CHANGE STATE - VALUES OF OBJECT, ONLY USING METHODS
 	-> visibility: public, protected, default, private
	- we use methods to access private properties getName() 

 - static field - associated with the class, opposite to instance fields 
 
 ## Inheritance 
 	- visibility is important - protected - visible to subclasses
 							  - privat - invisible
 	- we can call super.superMethod() in the subclass method, to extend it
 	- constructors are not inherited -> the prgoram would crash if we don't specify subclass constructor -> use super() - Java inserts automatically 
 
  - IF THE PARENT CLASS DOESN'T HAVE THE DEFAULT CONSTRUCOTR, AND WE DON'T CALL CONSTRUCTOR IN SUBCLASS, IT WILL NOT COMPILE
 	- becausee Java automatically inserts super() to the constructor	
 	 	
 - we can do mulitple constructors (overloading), based on what arguments we have 
 	-> to avoid WET code, we call previous constructor: this(0, 0, 1)
  
 - mehtods defined in the java.lang.Object CLASS! (static methods) - every object inherits:
 	- toString() - generates a String representations - memory adress by deffault -> we usually override it
 	- equals()
 	- hashCode() 

 - each class exactly one superclass (in C++ can have more)
 - first superclass is Object

## Abstract classes
- some classes have 'holes' in them - methods must be overridden in subclasses
- this ensures that all subclasses meet a given (library) API (naming and parameters)
- we can't create instances of abstract classes, only instances of their subclasses
- whole abstract class or only one method? 

## Final classes
**polymorphism** - if expecting instance of A, it can be any subclass of A
- this can allow subclass injection attack - overriding behaviour of a crucial class (validation)
- also ensures predictable behaviour
- cannot be subclassed at all
- whole abstract class or only one method 
- some practises include having all variables inside a class final - unchgable by mistake
- constants - static final - attached to class instead of object, unchangable

## Interfaces
- data structure that specifies a public API, starndard that all implementing classes must honour
	- represents class relationships outside the main inheritence hierarchy - *super blue print*
	- contains methods signitures, and field types?
	- class wihtout state and implementations and interface - implement syntax
- in Java instead of multiple inheritence of classes, we can implement two interfaces in one class - *mulitple inheritence of type*
- solves problem with multiple inheritance of class state and implementations
- can also inherit (extend other intefaces) - not common
- also method can be technically implemented in interfaces,  as default - not common
 

 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
