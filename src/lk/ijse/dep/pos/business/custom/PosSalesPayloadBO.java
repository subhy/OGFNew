package lk.ijse.dep.pos.business.custom;

import lk.ijse.dep.pos.business.SuperBO;
import lk.ijse.dep.pos.entity.PosSalesPayload;

public interface PosSalesPayloadBO extends SuperBO {
    public PosSalesPayload savePosSalesPayload(PosSalesPayload posSalesPayload);
}
