#### The ways to create multiple threads in Java can as follows:

1. **By Extending the Thread Class:**

   Create a new class that extends the Thread class.
   Override the run() method in this subclass to define the code that will be executed in the new thread.
   Instantiate the subclass and call its start() method to begin the execution of the thread.

2. **By Implementing the Runnable Interface:**

   Define a class that implements the Runnable interface.
   Implement the run() method within this class to provide the thread's task.
   Create an instance of your Runnable class, pass it to the constructor of a Thread object, and then start the thread by calling the start() method on the Thread instance.

3. **Using Lambda Expressions (since Java 8):**

   Directly pass a lambda expression or method reference implementing the Runnable interface to the constructor of a Thread object.
   Start the thread with the start() method.

4. **With Executor Framework (since Java 5):**

   Utilize the ExecutorService, typically by calling Executors.newFixedThreadPool(n) to create a thread pool.
   Submit tasks (either Runnable or Callable instances) to the ExecutorService using execute() for Runnable tasks or submit() for Callable tasks which return a Future.
   These are the primary mechanisms for creating and managing multiple threads in Java.