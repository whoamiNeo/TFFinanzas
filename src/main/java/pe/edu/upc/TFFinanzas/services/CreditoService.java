package pe.edu.upc.TFFinanzas.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.upc.TFFinanzas.dtos.CreditoTDO;
import pe.edu.upc.TFFinanzas.dtos.auth.ResponseDTO;
import pe.edu.upc.TFFinanzas.entities.Credito;
import pe.edu.upc.TFFinanzas.entities.Cliente;
import pe.edu.upc.TFFinanzas.entities.TipoInteresEnum;
import pe.edu.upc.TFFinanzas.entities.TipoCreditoEnum;
import pe.edu.upc.TFFinanzas.repositories.CreditoRepository;
import pe.edu.upc.TFFinanzas.repositories.ClienteRespositoy;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;



@Service
@RequiredArgsConstructor
public class CreditoService {
    private final CreditoRepository creditoRepository;
    private final ClienteRespositoy clienteRepository;

    //TASAS DE INTERES PARA CORTO Y LARGO PLAZO
    private static final double TASA_INTERES_QUINCENAL_CORTO_PLAZO = 0.10;
    private static final double TASA_INTERES_ANUAL_LARGO_PLAZO = 0.10;

    /*
    //FUNCION PARA CALCULAR EL MONTO TOTAL
    private double calcularMontoTotal(Credito credito) {
        if (credito.getTipoInteres() == TipoInteresEnum.CORTO_PLAZO && credito.getTipoCredito() == TipoCreditoEnum.NOMINAL) {
            long numeroQuincenas = ChronoUnit.DAYS.between(credito.getFechaInicio(), credito.getFechaFin()) / 15;
            double montoTotal = credito.getMonto() * Math.pow((1 + TASA_INTERES_QUINCENAL_CORTO_PLAZO), (numeroQuincenas/24));
            montoInicial * Math.pow((1 + tasaInteres), numeroPeriodos);
            return montoTotal;
        } else if (credito.getTipoInteres() == TipoInteresEnum.LARGO_PLAZO && credito.getTipoCredito() == TipoCreditoEnum.NOMINAL) {
            long numeroMeses = ChronoUnit.MONTHS.between(credito.getFechaInicio(), credito.getFechaFin());
            double tasaInteresMensualLargoPlazo = Math.pow((1 + TASA_INTERES_ANUAL_LARGO_PLAZO), (1.0 / 12)) - 1;
            double montoTotal = credito.getMonto() * Math.pow((1 + tasaInteresMensualLargoPlazo), (numeroMeses/12));
            return montoTotal;
        } else {
            throw new IllegalArgumentException("Tipo de interés no soportado");
        }
    }
    //FUNCION PARA CALCULAR SOLO EL INTERES A COBRAR
    private double calcularInteres(Credito credito) {
        if (credito.getTipoInteres() == TipoInteresEnum.CORTO_PLAZO && credito.getTipoCredito() == TipoCreditoEnum.NOMINAL) {
            long numeroQuincenas = ChronoUnit.DAYS.between(credito.getFechaInicio(), credito.getFechaFin()) / 15;
            double montoTotal = credito.getMonto() * Math.pow((1 + TASA_INTERES_QUINCENAL_CORTO_PLAZO), numeroQuincenas);
            double interesPagado = montoTotal - credito.getMonto();
            return interesPagado;
        } else if (credito.getTipoInteres() == TipoInteresEnum.LARGO_PLAZO && credito.getTipoCredito() == TipoCreditoEnum.NOMINAL) {
            long numeroMeses = ChronoUnit.MONTHS.between(credito.getFechaInicio(), credito.getFechaFin());
            double tasaInteresMensualLargoPlazo = Math.pow((1 + TASA_INTERES_ANUAL_LARGO_PLAZO), (1.0 / 12)) - 1;
            double montoTotal = credito.getMonto() * Math.pow((1 + tasaInteresMensualLargoPlazo), numeroMeses);
            double interesPagado = montoTotal - credito.getMonto();
            return interesPagado;
        } else {
            throw new IllegalArgumentException("Tipo de interés no soportado");
        }
    }

    //FUNCION PARA CALCULAR SOLO EL NUMERO DE CUOTAS
    private double calcularNumeroCuotas(Credito credito) {
        if (credito.getTipoInteres() == TipoInteresEnum.CORTO_PLAZO) {
            long numeroQuincenas = ChronoUnit.DAYS.between(credito.getFechaInicio(), credito.getFechaFin()) / 15;
            return numeroQuincenas;
        } else if (credito.getTipoInteres() == TipoInteresEnum.LARGO_PLAZO) {
            long numeroMeses = ChronoUnit.MONTHS.between(credito.getFechaInicio(), credito.getFechaFin());
            return numeroMeses;
        } else {
            throw new IllegalArgumentException("Tipo de interés no soportado");
        }
    }

    //FUNCION PARA CALCULAR EL MONTO A PAGAR POR MES
    private double calcularMontoXMes(Credito credito) {
        if (credito.getTipoInteres() == TipoInteresEnum.CORTO_PLAZO && credito.getTipoCredito() == TipoCreditoEnum.NOMINAL) {
            long numeroQuincenas = ChronoUnit.DAYS.between(credito.getFechaInicio(), credito.getFechaFin()) / 15;
            double montoTotal = credito.getMonto() * Math.pow((1 + TASA_INTERES_QUINCENAL_CORTO_PLAZO), numeroQuincenas);
            double MontoXMes = montoTotal/numeroQuincenas;
            return MontoXMes;
        } else if (credito.getTipoInteres() == TipoInteresEnum.LARGO_PLAZO && credito.getTipoCredito() == TipoCreditoEnum.NOMINAL) {
            long numeroMeses = ChronoUnit.MONTHS.between(credito.getFechaInicio(), credito.getFechaFin());
            double tasaInteresMensualLargoPlazo = Math.pow((1 + TASA_INTERES_ANUAL_LARGO_PLAZO), (1.0 / 12)) - 1;
            double montoTotal = credito.getMonto() * Math.pow((1 + tasaInteresMensualLargoPlazo), numeroMeses);
            double MontoXMes = montoTotal/numeroMeses;
            return MontoXMes;
        } else {
            throw new IllegalArgumentException("Tipo de interés no soportado");
        }
    }
    */

    //FUNCION PARA PASAR LA TASA NOMINAL A TASA EFECTIVA QUINCENAL
    private double calcularTasaEfectivaQuincenal() {
        double tasaEfectivaQuincenal = (Math.pow(1 + (TASA_INTERES_ANUAL_LARGO_PLAZO / 24), 1) - 1)*100;
        return tasaEfectivaQuincenal;
    }
    //FUNCION PARA PASAR LA TASA NOMNIAL A TASA EFECTIVA ANUAL
    private double calcularTasaEfectivaAnual() {
        double tasaEfectivaAnual = (Math.pow(1 + (TASA_INTERES_ANUAL_LARGO_PLAZO / 12), 12) - 1)*100;
        return tasaEfectivaAnual;
    }
    //FUNCION PARA PASAR DE TASA EFECTIVA ANUAL A TASA EFECTIVA MENSUAL
    private double calcularTasaEfectivaMensual() {
        double tasaEfectivaMensual = (Math.pow(1 + (calcularTasaEfectivaAnual()/100), 30 / 360) - 1)*100;
        return tasaEfectivaMensual;
    }

    //FUNCION QUE DEVUELVE UN ARREGLO CON EL MONTO TOTAL, INTERES A PAGAR, NUMERO DE CUOTAS, Monto A PAGAR X MES
    //TIPO 1 PARA TASA SIMPLE - TIPO 2 PARA TASA NOMINAL CREDITO CORTO - TIPO 3 PARA TASA NOMINAL CREDITO LARGO -
    // TIPO 4 PARA TASA EFECTIVA x PERIODO CORTO -  TIPO 5 PARA TASA EFECTIVA x PERIODO LARGO
    private double[] calcularInteresCompuesto(double montoInicial, double tasaInteres, int numeroPeriodos, int tipo, long dias) {
        double montoTotal;
        double interesPagado;
        long numeroCuotas;
        long numeroDias=dias;
        double montoXMes;
        double montoXQuincena;

        if(tipo == 1) {
            montoTotal = montoInicial * (1+ tasaInteres*(numeroDias/360));
            interesPagado = montoTotal - montoInicial;
            numeroCuotas = numeroPeriodos;
            montoXMes  = montoTotal/numeroCuotas;
            return new double[]{montoTotal, interesPagado, numeroCuotas, montoXMes};
        }
        else if(tipo == 2) {
            montoTotal = montoInicial * Math.pow((1 + tasaInteres), (numeroPeriodos/24));
            interesPagado = montoTotal - montoInicial;
            numeroCuotas = numeroPeriodos;
            double tasaNominalQuincenal = (1 * (Math.pow(1 + (calcularTasaEfectivaAnual()/100), 1 / 24) - 1)*100);
            montoXQuincena  = montoInicial * (tasaNominalQuincenal/100) / (1 - Math.pow(1 + (tasaNominalQuincenal/100), -numeroPeriodos));
            return new double[]{montoTotal, interesPagado, numeroCuotas, montoXQuincena};
        }
        else if(tipo == 3) {
            montoTotal = montoInicial * Math.pow((1 + tasaInteres), (numeroPeriodos/12));
            interesPagado = montoTotal - montoInicial;
            numeroCuotas = numeroPeriodos;
            double tasaNominalMensual = (1 * (Math.pow(1 + (calcularTasaEfectivaAnual()/100), 1 / 12) - 1)*100);
            montoXMes  = montoInicial * (tasaNominalMensual/100) / (1 - Math.pow(1 + (tasaNominalMensual/100), -numeroPeriodos));
            return new double[]{montoTotal, interesPagado, numeroCuotas, montoXMes};
        }
        else if(tipo == 4) {
            montoTotal = montoInicial * Math.pow(1 + (calcularTasaEfectivaAnual()/100), numeroDias / 360.0);
            interesPagado = montoTotal - montoInicial;
            numeroCuotas = numeroPeriodos;
            montoXQuincena  = montoInicial * (calcularTasaEfectivaQuincenal()/100) / (1 - Math.pow(1 + (calcularTasaEfectivaQuincenal()/100), -numeroPeriodos));
            return new double[]{montoTotal, interesPagado, numeroCuotas, montoXQuincena};
        }
        else {
            montoTotal = montoInicial * Math.pow(1 + (calcularTasaEfectivaAnual()/100), numeroDias / 360.0);
            interesPagado = montoTotal - montoInicial;
            numeroCuotas = numeroPeriodos;
            montoXMes = montoInicial * (calcularTasaEfectivaMensual()/100) / (1 - Math.pow(1 + (calcularTasaEfectivaMensual()/100), -numeroPeriodos));
            return new double[]{montoTotal, interesPagado, numeroCuotas, montoXMes};
        }
    }


    // FUNCIÓN PARA CALCULAR LOS DATOS
    private double[] calcularDatos(Credito credito) {
        int tipo; //1=tasa nominal //2=tasa efectiva
        if (credito.getTipoInteres() == TipoInteresEnum.CORTO_PLAZO && credito.getTipoCredito() == TipoCreditoEnum.SIMPLE) {
            long numeroQuincenas = ChronoUnit.DAYS.between(credito.getFechaInicio(), credito.getFechaFin()) / 14;
            long numeroDias = ChronoUnit.DAYS.between(credito.getFechaInicio(), credito.getFechaFin());
            return calcularInteresCompuesto(credito.getMonto(), TASA_INTERES_QUINCENAL_CORTO_PLAZO, (int) numeroQuincenas, 1, numeroDias);
        }
        else if (credito.getTipoInteres() == TipoInteresEnum.LARGO_PLAZO && credito.getTipoCredito() == TipoCreditoEnum.SIMPLE) {
            long numeroMeses = ChronoUnit.MONTHS.between(credito.getFechaInicio(), credito.getFechaFin());
            long numeroDias = ChronoUnit.DAYS.between(credito.getFechaInicio(), credito.getFechaFin());
            return calcularInteresCompuesto(credito.getMonto(), TASA_INTERES_ANUAL_LARGO_PLAZO, (int) numeroMeses, 1, numeroDias);
        }
        else if (credito.getTipoInteres() == TipoInteresEnum.CORTO_PLAZO && credito.getTipoCredito() == TipoCreditoEnum.NOMINAL) {
            long numeroQuincenas = ChronoUnit.DAYS.between(credito.getFechaInicio(), credito.getFechaFin()) / 14;
            long numeroDias = ChronoUnit.DAYS.between(credito.getFechaInicio(), credito.getFechaFin());
            return calcularInteresCompuesto(credito.getMonto(), TASA_INTERES_QUINCENAL_CORTO_PLAZO, (int) numeroQuincenas, 2, numeroDias);
        }
        else if (credito.getTipoInteres() == TipoInteresEnum.LARGO_PLAZO && credito.getTipoCredito() == TipoCreditoEnum.NOMINAL) {
            long numeroMeses = ChronoUnit.MONTHS.between(credito.getFechaInicio(), credito.getFechaFin());
            long numeroDias = ChronoUnit.DAYS.between(credito.getFechaInicio(), credito.getFechaFin());
            return calcularInteresCompuesto(credito.getMonto(), TASA_INTERES_ANUAL_LARGO_PLAZO, (int) numeroMeses, 3, numeroDias);
        }
        else if (credito.getTipoInteres() == TipoInteresEnum.CORTO_PLAZO && credito.getTipoCredito() == TipoCreditoEnum.EFECTIVO) {
            long numeroQuincenas = ChronoUnit.DAYS.between(credito.getFechaInicio(), credito.getFechaFin()) / 14;
            long numeroDias = ChronoUnit.DAYS.between(credito.getFechaInicio(), credito.getFechaFin());
            return calcularInteresCompuesto(credito.getMonto(), calcularTasaEfectivaAnual(), (int) numeroQuincenas, 4, numeroDias);
        }
        else if (credito.getTipoInteres() == TipoInteresEnum.LARGO_PLAZO && credito.getTipoCredito() == TipoCreditoEnum.EFECTIVO) {
            long numeroMeses = ChronoUnit.MONTHS.between(credito.getFechaInicio(), credito.getFechaFin());
            long numeroDias = ChronoUnit.DAYS.between(credito.getFechaInicio(), credito.getFechaFin());
            double tasaEfectivaAnual = calcularTasaEfectivaAnual();
            return calcularInteresCompuesto(credito.getMonto(), tasaEfectivaAnual, (int) numeroMeses, 5, numeroDias);
        }
        else {
            throw new IllegalArgumentException("Tipo de interés no soportado");
        }
    }



    //! REGISTRAR CREDITO
    public ResponseDTO registrarCredito(CreditoTDO creditoDTO) {
        Optional<Cliente> clienteOpt = clienteRepository.findById(creditoDTO.getIdCliente());
        if (!clienteOpt.isPresent()) {
            return new ResponseDTO("Cliente no encontrado");
        }
        Cliente cliente = clienteOpt.get();

        Credito credito = new Credito();
        credito.setMonto(creditoDTO.getMonto());
        credito.setFechaInicio(creditoDTO.getFechaInicio());
        credito.setFechaFin(creditoDTO.getFechaFin());
        credito.setEstado(creditoDTO.getEstado());
        credito.setCuotas(creditoDTO.getCuotas());
        credito.setTipoInteres(creditoDTO.getTipoInteres());
        credito.setTipoCredito(creditoDTO.getTipoCredito());
        credito.setCliente(cliente);

        double[] datos = calcularDatos(credito);
        System.out.println("Los datos son:");
        for (double dato : datos) {
            System.out.println(dato);
        } //PARA COMPROBAR LOS DATOS

        creditoRepository.save(credito);
        return new ResponseDTO("Crédito registrado correctamente");
    }
      //! ACTUALIZAR CREDITO
    public ResponseDTO actualizarCredito(Long idCredito, CreditoTDO creditoDTO) {
        Optional<Credito> creditoOpt = creditoRepository.findById(idCredito);
        if (creditoOpt.isPresent()) {
            Credito credito = creditoOpt.get();
            credito.setMonto(creditoDTO.getMonto());
            credito.setFechaInicio(creditoDTO.getFechaInicio());
            credito.setFechaFin(creditoDTO.getFechaFin());
            credito.setEstado(creditoDTO.getEstado());
            credito.setCuotas(creditoDTO.getCuotas());
            credito.setTipoInteres(creditoDTO.getTipoInteres());
            credito.setTipoCredito(creditoDTO.getTipoCredito());
            creditoRepository.save(credito);
            return new ResponseDTO("Crédito actualizado correctamente");
        }
        return new ResponseDTO("Crédito no encontrado");
    }
    //*aqui agregar una lista de las personas que tienen creditos pendientes o cosas asi 
       // LISTAR TODOS LOS CREDITOS
    public List<CreditoTDO> listarCreditos() {
        List<Credito> creditos = creditoRepository.findAll();
        return creditos.stream()
                .map(credito -> new CreditoTDO(
                        credito.getIdCredito(),
                        credito.getMonto(),
                        credito.getFechaInicio(),
                        credito.getFechaFin(),
                        credito.getEstado(),
                        credito.getCuotas(),
                        credito.getTipoInteres(),
                        credito.getTipoCredito(),
                        credito.getCliente().getIdCliente()))
                .collect(Collectors.toList());
    }

    // ELIMINAR CREDITO
    public ResponseDTO eliminarCredito(Long idCredito) {
        Optional<Credito> creditoOpt = creditoRepository.findById(idCredito);
        if (creditoOpt.isPresent()) {
            creditoRepository.delete(creditoOpt.get());
            return new ResponseDTO("Crédito eliminado correctamente");
        }
        return new ResponseDTO("Crédito no encontrado");
    }
}

