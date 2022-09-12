package com.gvelesiani.domain.repositories

interface ScreenshotsRepository {
    fun preventTakingScreenshots(prevent: Boolean)
    fun getTakingScreenshotsStatus(): Boolean
}