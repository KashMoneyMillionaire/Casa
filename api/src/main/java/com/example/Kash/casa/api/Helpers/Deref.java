package com.example.Kash.casa.api.Helpers;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.googlecode.objectify.Ref;

import java.util.List;

/**
 * Created by Kash on 2/15/2015.
 * Do work homie.
 */
public class Deref {
    public static class Func<T> implements Function<Ref<T>, T>
    {
        public static Func<Object> INSTANCE = new Func<>();

        @Override
        public T apply(Ref<T> ref) {
            return deRef(ref);
        }
    }

    public static <T> T deRef(Ref<T> ref) {
        return ref == null ? null : ref.get();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <T> List<T> deRef(List<Ref<T>> refList) {
        return Lists.transform(refList, (Func) Func.INSTANCE);
    }
}
