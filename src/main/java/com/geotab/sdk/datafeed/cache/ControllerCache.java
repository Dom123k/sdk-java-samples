package com.geotab.sdk.datafeed.cache;

import com.geotab.api.GeotabApi;
import com.geotab.http.request.AuthenticatedRequest;
import com.geotab.http.request.param.SearchParameters;
import com.geotab.http.response.ControllerListResponse;
import com.geotab.model.entity.controller.Controller;
import com.geotab.model.entity.controller.NoController;
import com.geotab.model.search.ControllerSearch;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@link Controller} cache singleton. Reloads controllers periodically on demand and caches them.
 */
public final class ControllerCache extends GeotabEntityCache<Controller> {

  private static final Logger log = LoggerFactory.getLogger(ControllerCache.class);

  public ControllerCache(GeotabApi api) {
    super(api, NoController.getInstance());
  }

  @Override
  protected Logger getLog() {
    return log;
  }

  @Override
  protected Optional<Controller> fetchEntity(String id) {
    log.debug("Loading Controller by id {} from Geotab ...", id);

    AuthenticatedRequest<?> request = AuthenticatedRequest.authRequestBuilder()
        .method("Get")
        .params(SearchParameters.searchParamsBuilder()
            .search(ControllerSearch.builder()
                .id(id)
                .build())
            .typeName("Controller")
            .build())
        .build();

    Optional<List<Controller>> controllers = api.call(request, ControllerListResponse.class);

    if (controllers.isPresent() && !controllers.get().isEmpty()) {
      log.debug("Controller by id {} loaded from Geotab.", id);
      return Optional.of(controllers.get().get(0));
    }

    return Optional.empty();
  }

  @Override
  protected Optional<List<Controller>> fetchAll() {
    log.debug("Loading all Controllers from Geotab ...");
    AuthenticatedRequest<?> request = AuthenticatedRequest.authRequestBuilder()
        .method("Get")
        .params(SearchParameters.searchParamsBuilder()
            .search(ControllerSearch.builder()
                .build())
            .typeName("Controller")
            .build())
        .build();

    return api.call(request, ControllerListResponse.class);
  }

  @Override
  protected Controller createFakeCacheable(String id) {
    log.debug("No Controller with id {} found in Geotab; creating a fake Controller to cache it.",
        id);
    return Controller.builder().id(id).build();
  }

}
