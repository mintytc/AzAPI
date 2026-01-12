package me.mintytc.azapi.api.classes.arrays;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @param <T> The type of object stored in the Enumerator.
 *
 * @since 1.0.0-R0.1
 *
 */
public class PaginatedEnumerator<T> implements Iterable<T> {

    private final Enumerator<T> source;
    private final int page;
    private final int itemsPerPage;
    private final List<Enumerator.Element<T>> currentPageElements;
    private final int totalPages;

    public PaginatedEnumerator(@NotNull Enumerator<T> source, int page, int itemsPerPage) {
        this.source = source;
        this.page = Math.max(1, page);
        this.itemsPerPage = Math.max(1, itemsPerPage);

        int total = source.size();
        this.totalPages = (int) Math.ceil((double) total / this.itemsPerPage);

        int fromIndex = (this.page - 1) * this.itemsPerPage;
        int toIndex = Math.min(fromIndex + this.itemsPerPage, total);

        this.currentPageElements = fromIndex >= total ? Collections.emptyList() :
                source.getElements().subList(fromIndex, toIndex);
    }

    public List<Enumerator.Element<T>> getElements() {
        return Collections.unmodifiableList(currentPageElements);
    }

    public int getPage() {
        return page;
    }

    public int getItemsPerPage() {
        return itemsPerPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public boolean hasNextPage() {
        return page < totalPages;
    }

    public boolean hasPreviousPage() {
        return page > 1;
    }

    public Enumerator<T> toEnumerator() {
        Enumerator<T> enumerator = new Enumerator<>();
        currentPageElements.forEach(e -> enumerator.add(e.getValue()));
        return enumerator;
    }

    public List<T> toList() {
        return currentPageElements.stream().map(Enumerator.Element::getValue).collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    public T[] toArray(Class<T> clazz) {
        T[] array = (T[]) java.lang.reflect.Array.newInstance(clazz, currentPageElements.size());
        for (int i = 0; i < currentPageElements.size(); i++) {
            array[i] = currentPageElements.get(i).getValue();
        }
        return array;
    }

    public boolean isEmpty() {
        return currentPageElements.isEmpty();
    }

    public int size() {
        return currentPageElements.size();
    }

    // Allows action on each element (full page context)
    public PaginatedEnumerator<T> forEachOnPage(Consumer<Enumerator.Element<T>> action) {
        currentPageElements.forEach(action);
        return this;
    }

    // Filters elements within this page (non-destructive)
    public PaginatedEnumerator<T> filter(Predicate<Enumerator.Element<T>> predicate) {
        Enumerator<T> temp = new Enumerator<>();
        currentPageElements.stream().filter(predicate).forEach(e -> temp.add(e.getValue()));
        return new PaginatedEnumerator<>(temp, 1, temp.size()); // just one filtered page
    }

    // Returns true if any element in the page matches the condition
    public boolean anyMatch(Predicate<Enumerator.Element<T>> predicate) {
        return currentPageElements.stream().anyMatch(predicate);
    }

    // Finds the first match in this page
    public Optional<Enumerator.Element<T>> findFirst(Predicate<Enumerator.Element<T>> predicate) {
        return currentPageElements.stream().filter(predicate).findFirst();
    }

    // Returns a Stream of page elements
    public Stream<Enumerator.Element<T>> stream() {
        return currentPageElements.stream();
    }

    // Maps current page to a list of another type
    public <R> List<R> map(Function<Enumerator.Element<T>, R> mapper) {
        return currentPageElements.stream().map(mapper).collect(Collectors.toList());
    }

    // Gets raw values of current page
    public List<T> values() {
        return currentPageElements.stream().map(Enumerator.Element::getValue).collect(Collectors.toList());
    }

    // Checks if page contains a specific value
    public boolean containsValue(T value) {
        return currentPageElements.stream().anyMatch(e -> Objects.equals(e.getValue(), value));
    }

    // Gets element at relative page index
    public Optional<Enumerator.Element<T>> get(int index) {
        if (index < 0 || index >= currentPageElements.size()) return Optional.empty();
        return Optional.of(currentPageElements.get(index));
    }

    // Searches the elements by a given condition (predicate) and returns a new paginated result
    public PaginatedEnumerator<T> search(Predicate<Enumerator.Element<T>> condition, int page, int itemsPerPage) {
        Enumerator<T> filteredEnumerator = new Enumerator<>();
        currentPageElements.stream()
                .filter(condition)
                .forEach(e -> filteredEnumerator.add(e.getValue()));

        return new PaginatedEnumerator<>(filteredEnumerator, page, itemsPerPage);
    }

    @Override
    public String toString() {
        return "PaginatedEnumerator{page=" + page + ", totalPages=" + totalPages + ", size=" + size() + "}";
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public @NotNull Iterator<T> iterator() {
        return toList().iterator();
    }

    /**
     * Performs the given action for each element of the {@code Iterable}
     * until all elements have been processed or the action throws an
     * exception.  Actions are performed in the order of iteration, if that
     * order is specified.  Exceptions thrown by the action are relayed to the
     * caller.
     * <p>
     * The behavior of this method is unspecified if the action performs
     * side-effects that modify the underlying source of elements, unless an
     * overriding class has specified a concurrent modification policy.
     *
     * @param action The action to be performed for each element
     *
     * @throws NullPointerException if the specified action is null
     * @implSpec <p>The default implementation behaves as if:
     * <pre>{@code
     *     for (T t : this)
     *         action.accept(t);
     * }</pre>
     * @since 1.8
     */
    @Override
    public void forEach(Consumer<? super T> action) {
        toList().forEach(action);
    }

    /**
     * Creates a {@link Spliterator} over the elements described by this
     * {@code Iterable}.
     *
     * @return a {@code Spliterator} over the elements described by this
     * {@code Iterable}.
     * @implSpec The default implementation creates an
     * <em><a href="../util/Spliterator.html#binding">early-binding</a></em>
     * spliterator from the iterable's {@code Iterator}.  The spliterator
     * inherits the <em>fail-fast</em> properties of the iterable's iterator.
     * @implNote The default implementation should usually be overridden.  The
     * spliterator returned by the default implementation has poor splitting
     * capabilities, is unsized, and does not report any spliterator
     * characteristics. Implementing classes can nearly always provide a
     * better implementation.
     * @since 1.8
     */
    @Override
    public Spliterator<T> spliterator() {
        return toList().spliterator();
    }
}