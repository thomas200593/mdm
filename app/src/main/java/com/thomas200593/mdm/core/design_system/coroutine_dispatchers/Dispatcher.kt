package com.thomas200593.mdm.core.design_system.coroutine_dispatchers

import javax.inject.Qualifier

@Qualifier @Retention(AnnotationRetention.RUNTIME) annotation class Dispatcher(val coroutineDispatchers: CoroutineDispatchers)