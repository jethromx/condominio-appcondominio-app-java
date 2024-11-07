package com.core.coffee.entity;

import java.util.Date;

public class BankStatement {

    private String id;
    private Condominium condominium;
    private Date initialDate;
    private Date finalDate;
/*
    saldoAnterior: Saldo que quedó pendiente del periodo anterior
totalCargos: Suma de todos los cargos aplicados en el periodo
totalPagos: Suma de todos los pagos realizados en el periodo
saldoFinal: Saldo resultante después de aplicar cargos y pagos en el periodo
estado: Estado del balance (pendiente, pagado, atrasado)

*/

    
}
/*
 * 
 * 
 
 Modelo de Entidades para el Estado de Cuenta
1. EstadoDeCuenta
Descripción: Representa el estado financiero actual de una unidad o usuario en un periodo específico, con un resumen de cargos y pagos realizados.

Propiedades:
id: Identificador único
unidadId: Identificador de la unidad a la que se le genera el estado de cuenta (relación con Unidad)
fechaInicio: Fecha de inicio del periodo del estado de cuenta
fechaFin: Fecha de fin del periodo del estado de cuenta
saldoAnterior: Saldo que quedó pendiente del periodo anterior
totalCargos: Suma de todos los cargos aplicados en el periodo
totalPagos: Suma de todos los pagos realizados en el periodo
saldoFinal: Saldo resultante después de aplicar cargos y pagos en el periodo
estado: Estado del balance (pendiente, pagado, atrasado)
2. Cargo
Descripción: Representa un cargo específico aplicado a la unidad, como cuotas de mantenimiento, reparaciones, o multas.

Propiedades:
id: Identificador único
tipo: Tipo de cargo (mantenimiento, multa, servicio extra, etc.)
monto: Monto del cargo
fecha: Fecha en que se aplicó el cargo
descripcion: Descripción o motivo del cargo
estadoCuentaId: Identificador del estado de cuenta donde se incluye el cargo (relación con EstadoDeCuenta)
3. Pago
Descripción: Representa un pago realizado por el usuario para cubrir un cargo o un conjunto de cargos. Esta entidad puede reutilizarse del modelo anterior.

Propiedades:
id: Identificador único
fechaPago: Fecha en que se realizó el pago
monto: Monto pagado
metodoPago: Método de pago utilizado (tarjeta, transferencia, etc.)
usuarioId: Identificador del usuario que realizó el pago
estadoCuentaId: Identificador del estado de cuenta al que se aplica el pago (relación con EstadoDeCuenta)
4. DetalleEstadoCuenta
Descripción: Representa un detalle de cargos y pagos específicos que componen el estado de cuenta en un periodo.

Propiedades:
id: Identificador único
estadoCuentaId: Identificador del estado de cuenta al que pertenece el detalle (relación con EstadoDeCuenta)
cargoId: Identificador del cargo incluido (relación con Cargo)
pagoId: Identificador del pago relacionado (relación con Pago, si se aplica a un cargo específico)
tipo: Indica si el detalle es un cargo o pago
monto: Monto del cargo o pago
descripcion: Descripción breve del cargo o pago
Relaciones y Estructura
EstadoDeCuenta tiene muchos DetallesEstadoCuenta para mostrar todos los cargos y pagos.
Cargo y Pago se registran en un DetalleEstadoCuenta para un desglose del estado de cuenta.
Unidad tiene un EstadoDeCuenta que refleja los movimientos financieros del periodo.
Flujo de Generación del Estado de Cuenta
Generar Estado de Cuenta:

Al final de un periodo (ej., mes), se genera un EstadoDeCuenta para cada unidad.
Se incluyen todos los Cargos y Pagos realizados durante ese periodo.
Calcular Saldos:

El EstadoDeCuenta muestra el saldoAnterior, el totalCargos y el totalPagos para calcular el saldoFinal.
Desglose Detallado:

Cada DetalleEstadoCuenta permite ver un desglose de cargos y pagos específicos para mayor transparencia.
 */
