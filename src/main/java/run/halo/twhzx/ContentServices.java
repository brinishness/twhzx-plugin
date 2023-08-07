package run.halo.twhzx;

import java.util.Comparator;
import java.util.function.Predicate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import run.halo.app.extension.Extension;
import run.halo.app.extension.GroupVersionKind;
import run.halo.app.extension.ListResult;
import run.halo.app.extension.Unstructured;

public interface ContentServices {
    <E extends Extension> Flux<E> list(Class<E> type, Predicate<E> predicate,
        Comparator<E> comparator);

    <E extends Extension> Mono<ListResult<E>> list(Class<E> type, Predicate<E> predicate,
        Comparator<E> comparator, int page, int size);

    <E extends Extension> Mono<E> fetch(Class<E> type, String name);

    Mono<Unstructured> fetch(GroupVersionKind gvk, String name);

    <E extends Extension> Mono<E> get(Class<E> type, String name);

    <E extends Extension> Mono<E> create(E extension);

    <E extends Extension> Mono<E> update(E extension);

    <E extends Extension> Mono<E> delete(E extension);
}
