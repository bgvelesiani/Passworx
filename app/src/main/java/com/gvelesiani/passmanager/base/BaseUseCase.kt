package com.gvelesiani.passmanager.base

abstract class BaseUseCase<Params, Result> {
    abstract suspend fun run(params: Params): Result
}