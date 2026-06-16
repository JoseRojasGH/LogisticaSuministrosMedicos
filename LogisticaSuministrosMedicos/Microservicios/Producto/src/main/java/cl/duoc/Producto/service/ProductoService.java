package cl.duoc.Producto.service;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.Producto.client.*;
import cl.duoc.Producto.dto.CategoriaDTO;
import cl.duoc.Producto.dto.InventarioDTO;
import cl.duoc.Producto.dto.ProductoDetalleDTO;
import cl.duoc.Producto.dto.ProveedorDTO;
import cl.duoc.Producto.dto.UsuarioDTO;
import cl.duoc.Producto.model.Categoria;
import cl.duoc.Producto.model.Producto;
import cl.duoc.Producto.repository.ProductoRepository;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private UsuarioClient usuarioClient;

    @Autowired
    private ProveedorClient proveedorClient;

    @Autowired
    private InventarioClient inventarioClient;

    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    public Producto crearProducto(Producto producto) {
        UsuarioDTO usuario = usuarioClient.obtenerUsuario(producto.getUsuarioId());
        if (usuario == null) {
            throw new RuntimeException("Usuario no existe");
        }

        InventarioDTO inventario = inventarioClient.obtenerInventario(producto.getInventarioId());
        if (inventario == null) {
            throw new RuntimeException("Inventario no existe");
        }

        ProveedorDTO proveedor = proveedorClient.obtenerProveedor(producto.getProveedorId());
        if (proveedor == null) {
            throw new RuntimeException("Proveedor no existe");
        }

        return productoRepository.save(producto);
    }

    public Producto buscarPorId(Integer id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    public List<Producto> buscarPorNombre(String nombre) {        
        return productoRepository.findByNombre(nombre);
    }

    public Double buscarPrecioPorId(Integer id){
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        return producto.getPrecio();
    }

    public Date buscarFechaVencimientoPorId(Integer id){
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        return producto.getFecha_vencimiento();
    }

    public void eliminarProducto(Integer id) {
        if(!productoRepository.existsById(id)){
            throw new RuntimeException("Producto no encontrado");
        }
        productoRepository.deleteById(id);
    }

    public void actualizarPrecioProducto(Integer id, Double nuevoPrecio) {
        if(!productoRepository.existsById(id)){
            throw new RuntimeException("Producto no encontrado");
        }
        Producto producto = productoRepository.findById(id).get();
        producto.setPrecio(nuevoPrecio);
        productoRepository.save(producto);
    }

    public ProductoDetalleDTO obtenerDetalleProducto(Integer id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        UsuarioDTO usuario = usuarioClient.obtenerUsuario(producto.getUsuarioId());

        if(usuario == null) {
            throw new RuntimeException("Usuario no encontrado");
        }

        ProveedorDTO proveedor = proveedorClient.obtenerProveedor(producto.getProveedorId());

        if(proveedor == null) {
            throw new RuntimeException("Proveedor no encontrado");
        }

        InventarioDTO inventario = inventarioClient.obtenerInventario(producto.getInventarioId());

        if(inventario == null) {
            throw new RuntimeException("Inventario no encontrado");
        }

        Categoria tipo = producto.getCategoria();

        CategoriaDTO categoriaDTO = new CategoriaDTO(
            tipo.getId(),
            tipo.getTipo_producto()
        );

        ProductoDetalleDTO dto = new ProductoDetalleDTO();
            dto.setId(producto.getId());
            producto.setNombre(producto.getNombre());
            dto.setNumero_lote(producto.getNumero_lote());
            dto.setPrecio(producto.getPrecio());
            dto.setFecha_vencimiento(producto.getFecha_vencimiento());
            dto.setInventario(inventario);
            dto.setUsuario(usuario);
            dto.setProveedor(proveedor);
            dto.setCategoria(categoriaDTO);

        return dto;
    }

}
