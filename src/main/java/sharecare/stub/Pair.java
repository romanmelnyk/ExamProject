package sharecare.stub;

import lombok.Value;

@Value
public class Pair<F, S> {
    F first;
    S second;

    public static <FF, SS> Pair<FF, SS> p(FF first, SS second) {
        return new Pair(first, second);
    }
}
