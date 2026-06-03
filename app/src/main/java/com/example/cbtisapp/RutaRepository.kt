package com.example.cbtisapp

object RutaRepository {

    fun obtenerVideoResource(edificioId: String): Int {
        return when (edificioId) {
            // --- ZONA CANCHAS ---
            "a_canchas" -> R.raw.ruta_a_canchas
            "b_canchas" -> R.raw.ruta_b_canchas
            "cafe_canchas" -> R.raw.ruta_cafe_canchas
            "ebc_canchas" -> R.raw.ruta_ebc_canchas
            "em_canchas" -> R.raw.ruta_em_canchas
            "moto_canchas" -> R.raw.ruta_moto_canchas
            "rb_canchas" -> R.raw.ruta_rob_canchas

            // --- ZONA BICÉFALO ---
            "el_bicefalo" -> R.raw.ruta_el_bicefalo
            "j_bicefalo" -> R.raw.ruta_j_bicefalo
            "cp_bicefalo" -> R.raw.ruta_cm_bicefalo
            "s_bicefalo" -> R.raw.ruta_s_bicefalo

            // --- ZONA CASETA ---
            "c_caseta" -> R.raw.ruta_c_caseta
            "ad_caseta" -> R.raw.ruta_ms_caseta
            "biblio_caseta" -> R.raw.ruta_biblio_caseta
            "control_caseta" -> R.raw.ruta_control_caseta

            else -> R.raw.ruta_cafe_canchas
        }
    }
}
