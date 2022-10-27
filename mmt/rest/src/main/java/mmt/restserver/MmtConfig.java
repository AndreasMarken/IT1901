package mmt.restserver;

public class MmtConfig {

    private MovieList movieList extends ResourceConfig;


    public TodoConfig(TodoModel todoModel) {
        setTodoModel(todoModel);
        todoPersistence = new TodoPersistence();
        todoPersistence.setSaveFile("server-todolist.json");
        register(TodoModelService.class);
        register(TodoModuleObjectMapperProvider.class);
        register(JacksonFeature.class);
        register(new AbstractBinder() {
          @Override
          protected void configure() {
            bind(TodoConfig.this.todoModel);
            bind(TodoConfig.this.todoPersistence);
          }
        });
      }
    
}
