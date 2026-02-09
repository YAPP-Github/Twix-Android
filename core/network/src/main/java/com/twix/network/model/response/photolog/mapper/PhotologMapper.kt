package com.twix.network.model.response.photolog.mapper

import com.twix.domain.model.photolog.PhotoLogs
import com.twix.domain.model.photolog.PhotologDetail
import com.twix.network.model.response.photolog.PhotoLogResponse
import com.twix.network.model.response.photolog.PhotologDetailResponse

fun PhotologDetailResponse.toDomain(): PhotologDetail =
    PhotologDetail(
        comment = comment,
        goalId = goalId,
        imageUrl = imageUrl,
        isMine = isMine,
        photologId = photologId,
        uploadedAt = uploadedAt,
        uploaderName = uploaderName,
        verificationDate = verificationDate,
    )

fun PhotoLogResponse.toDomain(): PhotoLogs =
    PhotoLogs(
        goalId = goalId,
        myNickname = myNickname,
        partnerNickname = partnerNickname,
        goalTitle = goalTitle,
        photologDetails = photologs.map { it.toDomain() },
    )
