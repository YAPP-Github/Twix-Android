package com.twix.domain.model.photolog

data class PhotoLogs(
    val goalId: Long,
    val myNickname: String,
    val partnerNickname: String,
    val photologDetails: List<PhotologDetail>,
) {
    val myPhotologs: PhotologDetail
        get() = photologDetails.first { it.isMine }

    val isMyCertificated: Boolean
        get() = myPhotologs.imageUrl.isBlank()

    val partnerPhotologs: PhotologDetail
        get() = photologDetails.first { !it.isMine }

    val isPartnerCertificated: Boolean
        get() = partnerPhotologs.imageUrl.isBlank()

    companion object {
        val EMPTY =
            PhotoLogs(
                goalId = -1,
                myNickname = "",
                partnerNickname = "",
                photologDetails = emptyList(),
            )
    }
}
