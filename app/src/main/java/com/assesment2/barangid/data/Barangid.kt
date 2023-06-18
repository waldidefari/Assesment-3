package com.assesment2.barangid.data

import android.app.Application


class Barangid : Application() {
    val database: BarangDb by lazy { BarangDb.getDatabase(this) }
}
