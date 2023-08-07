package run.halo.twhzx;

import java.util.Comparator;
import java.util.function.Predicate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import run.halo.app.extension.Extension;
import run.halo.app.extension.GroupVersionKind;
import run.halo.app.extension.ListResult;
import run.halo.app.extension.ReactiveExtensionClient;
import run.halo.app.extension.Unstructured;

@Service
public class ContentServicesImpl implements ContentServices {

    private final ReactiveExtensionClient client;

    public ContentServicesImpl(ReactiveExtensionClient client) {
        this.client = client;
    }


    @Override
    public <E extends Extension> Flux<E> list(Class<E> type, Predicate<E> predicate,
        Comparator<E> comparator) {
        return client.list(type, predicate, comparator);
    }

    @Override
    public <E extends Extension> Mono<ListResult<E>> list(Class<E> type, Predicate<E> predicate,
        Comparator<E> comparator, int page, int size) {
        return client.list(type, predicate, comparator, page, size);
    }

    @Override
    public <E extends Extension> Mono<E> fetch(Class<E> type, String name) {
        return null;
    }

    @Override
    public Mono<Unstructured> fetch(GroupVersionKind gvk, String name) {
        return null;
    }

    @Override
    public <E extends Extension> Mono<E> get(Class<E> type, String name) {
        return null;
    }

    @Override
    public <E extends Extension> Mono<E> create(E extension) {
        return null;
    }

    @Override
    public <E extends Extension> Mono<E> update(E extension) {
        return null;
    }

    @Override
    public <E extends Extension> Mono<E> delete(E extension) {
        return null;
    }
}
