package com.example.kwms.inbound.domain;

import com.example.kwms.common.BadRequestException;

public class AlreadyExistLPNException extends BadRequestException {
    private static final String MESSAGE = "이미 등록된 LPN 바코드입니다. LPN 바코드: %s";

    AlreadyExistLPNException(final String lpnBarcode) {
        super(MESSAGE.formatted(lpnBarcode));
    }
}
