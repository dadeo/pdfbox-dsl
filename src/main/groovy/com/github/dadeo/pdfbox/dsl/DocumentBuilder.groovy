package com.github.dadeo.pdfbox.dsl

import com.github.dadeo.pdfbox.model.*

class DocumentBuilder extends FactoryBuilderSupport {
    protected void setParent(Object parent, Object child) {
        super.setParent(parent, child)
    }

    protected Factory resolveFactory(Object name, Map attributes, Object value) {
        switch (name) {
            case 'document':
                return new DocumentBuilderFactory()
            case 'page':
                return new PageBuilderFactory()
            case 'table':
                return new TableBuilderFactory()
            case 'cell':
                return new CellBuilderFactory()
            case 'paragraph':
                return new ParagraphBuilderFactory()
            case 'part':
                return new PartBuilderFactory()
            case 'hr':
                return new HorizontalRuleBuilderFactory()
            default:
                throw new UnsupportedOperationException("DocumentBuilder does not support building '$name' objects.")
        }
    }

    static class DocumentBuilderFactory extends AbstractFactory {

        Object newInstance(FactoryBuilderSupport factoryBuilderSupport, Object o, Object o1, Map map) throws InstantiationException, IllegalAccessException {
            new DDocument()
        }

    }

    static class PageBuilderFactory extends AbstractFactory {

        Object newInstance(FactoryBuilderSupport factoryBuilderSupport, Object o, Object o1, Map map) throws InstantiationException, IllegalAccessException {
            new DPage()
        }

        void setParent(FactoryBuilderSupport builder, Object parent, Object child) {
            parent << child
        }
    }

    static class TableBuilderFactory extends AbstractFactory {

        Object newInstance(FactoryBuilderSupport factoryBuilderSupport, Object o, Object o1, Map map) throws InstantiationException, IllegalAccessException {
            return new Table()
        }

        void setParent(FactoryBuilderSupport builder, Object parent, Object child) {
            parent << child
        }
    }

    static class CellBuilderFactory extends AbstractFactory {

        Object newInstance(FactoryBuilderSupport factoryBuilderSupport, Object o, Object text, Map map) throws InstantiationException, IllegalAccessException {
            Cell cell = new Cell()
            if (text) {
                cell << new DParagraph(text.toString())
            }
            if (map.containsKey('text')) {
                cell << new DParagraph(map.text.toString())
                map.remove('text')
            }
            cell
        }

        void setParent(FactoryBuilderSupport builder, Object parent, Object child) {
            parent << child
        }
    }

    static class ParagraphBuilderFactory extends AbstractFactory {

        Object newInstance(FactoryBuilderSupport factoryBuilderSupport, Object objectType, Object text, Map map) throws InstantiationException, IllegalAccessException {
            DParagraph paragraph = new DParagraph()
            if (text) {
                paragraph << new DPart(text.toString())
            }
            if (map.containsKey('text')) {
                paragraph << new DPart(map.text.toString())
                map.remove('text')
            }
            paragraph
        }

        void setParent(FactoryBuilderSupport builder, Object parent, Object child) {
            parent << child
        }
    }

    static class PartBuilderFactory extends AbstractFactory {

        Object newInstance(FactoryBuilderSupport factoryBuilderSupport, Object objectType, Object text, Map map) throws InstantiationException, IllegalAccessException {
            text ? new DPart(text: text) : new DPart()
        }

        void setParent(FactoryBuilderSupport builder, Object parent, Object child) {
            parent << child
        }
    }

    static class HorizontalRuleBuilderFactory extends AbstractFactory {

        Object newInstance(FactoryBuilderSupport factoryBuilderSupport, Object o, Object o1, Map map) throws InstantiationException, IllegalAccessException {
            new DHorizontalRule()
        }

        void setParent(FactoryBuilderSupport builder, Object parent, Object child) {
            parent << child
        }
    }
}