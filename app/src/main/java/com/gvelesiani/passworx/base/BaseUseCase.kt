package com.gvelesiani.passworx.base

abstract class BaseUseCase<Params, Result> {
    abstract suspend fun run(params: Params): Result
}