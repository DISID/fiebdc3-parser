package com.disid.fiebdc3;

/**
 * A {@link Measurement} measurement.
 * 
 * @author DiSiD Team
 */
public class MeasurementLine {

    Integer type;

    String comment;

    Float units;

    Float length;

    Float width;

    Float height;

    /**
     * Spec definition:<br/>
     * <i>/* TIPO: Indica el tipo de línea de medición de que se trate.
     * Usualmente este subcampo estará vacío. Los tipos establecidos en esta
     * VERSION son: '1': Subtotal parcial: En esta línea aparecerá el
     * subtotal de las líneas anteriores desde el último subtotal hasta la
     * línea inmediatamente anterior a ésta. '2': Subtotal acumulado: En
     * esta línea aparecerá el subtotal de todas las líneas anteriores desde
     * la primera hasta la línea inmediatamente anterior a ésta. '3':
     * Expresión: Indicará que en el subcampo COMENTARIO aparecerá una
     * expresión algebraica a evaluar. Se podrán utilizar los operadores
     * '(', ')', '+', '-', '*', '/' y '^'; las variables 'a', 'b', 'c' y 'd'
     * (que tendrán por valor las cantidades introducidas en los subcampos
     * UNIDADES, LONGITUD, LATITUD y ALTURA respectivamente); y la constante
     * 'p' para el valor Pi=3.1415926. Esta expresión será válida hasta la
     * siguiente línea de medición en la que se defina otra expresión. Solo
     * se evalúa la expresión y no se multiplica por las unidades. Las
     * expresiones fórmulas utilizan los criterios definidos en el anexo 2.
     * </i>
     */
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * Spec definition:<br/>
     * <i>COMENTARIO: Texto en la línea de medición. Podrá ser un comentario
     * o una expresión algebraica.</i>
     */
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Spec definition:<br/>
     * <i>UNIDADES, LONGITUD, LATITUD, ALTURA: Cuatro número reales con las
     * mediciones. Si alguna magnitud no existe se dejará este campo
     * vacío</i>
     */
    public Float getUnits() {
        return units;
    }

    public void setUnits(Float units) {
        this.units = units;
    }

    /**
     * Spec definition:<br/>
     * <i>UNIDADES, LONGITUD, LATITUD, ALTURA: Cuatro número reales con las
     * mediciones. Si alguna magnitud no existe se dejará este campo
     * vacío</i>
     */
    public Float getLength() {
        return length;
    }

    public void setLength(Float length) {
        this.length = length;
    }

    /**
     * Spec definition:<br/>
     * <i>UNIDADES, LONGITUD, LATITUD, ALTURA: Cuatro número reales con las
     * mediciones. Si alguna magnitud no existe se dejará este campo
     * vacío</i>
     */
    public Float getWidth() {
        return width;
    }

    public void setWidth(Float width) {
        this.width = width;
    }

    /**
     * Spec definition:<br/>
     * <i>UNIDADES, LONGITUD, LATITUD, ALTURA: Cuatro número reales con las
     * mediciones. Si alguna magnitud no existe se dejará este campo
     * vacío</i>
     */
    public Float getHeight() {
        return height;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "Measurment {" + "Type: " + type + ", Comment: " + comment
                + ", Units: " + units + ", Length: " + length + ", Width: "
                + width + ", Height: " + height + "}";
    }

}