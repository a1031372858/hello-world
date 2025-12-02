package org.example.converter;

import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseConverter<TO, PO> {
    public BaseConverter() {
    }

    public abstract TO convertT(PO var1);

    public abstract PO convertP(TO var1);

    public TO po2to(PO po) {
        return this.convertT(po);
    }

    public PO to2po(TO to) {
        return this.convertP(to);
    }

    public List<TO> poList2TOList(List<PO> poList) {
        return (List)poList.stream().map(this::convertT).collect(Collectors.toList());
    }

    public List<PO> toList2POList(List<TO> toList) {
        return (List)toList.stream().map(this::convertP).collect(Collectors.toList());
    }
}