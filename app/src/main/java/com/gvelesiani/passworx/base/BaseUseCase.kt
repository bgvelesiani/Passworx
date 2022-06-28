package com.gvelesiani.passworx.base

abstract class BaseUseCase<Params, Result> {
    abstract suspend fun invoke(params: Params): Result
}