package mmt.restserver;

/**
 * Provides the Jackson module used for JSON serialization.
 */
@Provider
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TodoModuleObjectMapperProvider implements ContextResolver<ObjectMapper> {

  private final ObjectMapper objectMapper;

  public TodoModuleObjectMapperProvider() {
    // Uuse variant which only serializes list properties as part of TodoModel objects, and
    // not the contents, nor settings
    // The contents or settings are serialized as part of TodoList and TodoSettings objects
    objectMapper = TodoPersistence.createObjectMapper(EnumSet.of(TodoModelParts.LISTS));
  }

  @Override
  public ObjectMapper getContext(final Class<?> type) {
    return objectMapper;
  }
}


