package com.example.cbtisapp

object RutaRepository {

    fun obtenerVideoResource(edificioId: String): Int {
        return when (edificioId) {
            // --- ZONA CANCHAS (Todos listos) ---
            "a_canchas" -> R.raw.ruta_a_canchas
            "b_canchas" -> R.raw.ruta_b_canchas
            "cafe_canchas" -> R.raw.ruta_cafe_canchas
            "ebc_canchas" -> R.raw.ruta_ebc_canchas
            "em_canchas" -> R.raw.ruta_em_canchas
            "moto_canchas" -> R.raw.ruta_moto_canchas

            // --- ZONA BICÉFALO ---
            "el_bicefalo" -> R.raw.ruta_el_bicefalo

            "j_bicefalo", "cp_bicefalo", "s_bicefalo" -> R.raw.ruta_cafe_canchas

            // --- ZONA CASETA (Faltan por grabar/procesar) ---
            "rb_canchas", "c_caseta", "ad_caseta" -> R.raw.ruta_cafe_canchas

            else -> R.raw.ruta_cafe_canchas
        }
    }
}
